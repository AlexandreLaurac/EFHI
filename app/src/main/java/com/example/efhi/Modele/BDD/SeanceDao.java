package com.example.efhi.Modele.BDD;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SeanceDao {

    @Query("SELECT * FROM seance")
    List<Seance> getAll() ;

    @Query("SELECT * FROM seance WHERE categorie=:Categorie")
    List<Seance> getCategorie(String Categorie) ;

    @Query("SELECT * FROM seance WHERE categorie=\"last\"")
    List<Seance> getLast() ;

    @Query("SELECT COUNT(*) FROM seance")
    long getNbOfElements() ;

    @Query("SELECT MAX(id) FROM seance")
    long getMaxId() ;

    @Insert
    long insert(Seance seance) ;

    @Insert
    long[] insertAll(Seance... seance) ;

    @Delete
    void delete(Seance seance) ;

    @Update
    void update(Seance seance) ;

}
