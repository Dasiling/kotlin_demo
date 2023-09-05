package com.example.lesson

import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient.get
import com.example.core.utils.Utils.toast
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken

class LessonPresenter {
    companion object {
        private const val LESSON_PATH = "lessons"
    }

    private var activity: LessonActivity? = null

    constructor(activity: LessonActivity?) {
        this.activity = activity
    }

    private var lessons: List<Lesson?> = ArrayList()

    private val type = object : TypeToken<List<Lesson>>() {}.type

    fun fetchData() {
        get(
            LESSON_PATH, type,
            object : EntityCallback<List<Lesson>> {
                override fun onSuccess(lessons: List<Lesson>) {
                    this@LessonPresenter.lessons = lessons
                    activity!!.runOnUiThread(Runnable { activity!!.showResult(lessons) })
                }

                override fun onFailure(message: String?) {
                    activity!!.runOnUiThread(Runnable { toast(message!!) })
                }
            }
        )
    }

    fun showPlayback() {
        val playbackLessons = mutableListOf<Lesson>()
        for (lesson in lessons) {
            if (lesson?.state === Lesson.State.PLAYBACK) {
                playbackLessons.add(lesson)
            }
        }
        activity!!.showResult(playbackLessons)
    }
}