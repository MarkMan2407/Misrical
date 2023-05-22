package com.squizzard.converter.ui

import android.content.Context
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.installations.FirebaseInstallations
import com.squizzard.data.GetRemindersUseCase

/**
 * Copyright © 2023. Barrelproof Ltd. All rights reserved.
 * Created by mmansfield 22/05/2023.
 **/

class ReminderReporter(val context: Context) {

    fun reportAllReminders() {
        val collectionReference: CollectionReference =
            FirebaseFirestore.getInstance().collection("reminders")

        val allReminders = GetRemindersUseCase(context).reminders
        val r = ReminderDocument()
        FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                allReminders?.let {
                    r.count = it.size
                }
                collectionReference.document(task.result).set(r)
            }
        }
    }
}