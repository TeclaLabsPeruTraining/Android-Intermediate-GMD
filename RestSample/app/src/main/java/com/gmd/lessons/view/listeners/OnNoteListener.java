package com.gmd.lessons.view.listeners;


import com.gmd.lessons.entity.NoteEntity;
import com.gmd.lessons.storage.db.CRUDOperations;

/**
 * Created by emedinaa on 15/09/15.
 */
public interface OnNoteListener {

     CRUDOperations getCrudOperations();
     void deleteNote(NoteEntity noteEntity);
     void showParentLoading();
     void hideParentLoading();
     void showMessage(String message);
}
