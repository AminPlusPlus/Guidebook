package com.example.guidebook.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "GuideBook")
class Task {
    /*    private ArrayList<String> venue;*/

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var objType: String? = null
    var endDate: String? = null
    var startDate: String? = null
    var name: String? = null
    var isLoginRequired: Boolean = false
    var url: String? = null
    var icon: String? = null

    /*  public ArrayList getVenue() {
        return venue;
    }

    public void setVenue(ArrayList venue) {
        this.venue = venue;
    }*/
}