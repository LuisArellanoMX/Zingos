package com.zingos.base_datos

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ServicioDao {
    @Query("SELECT * FROM servicio")
    fun getAll(): LiveData<List<Servicio>>

    @Query("SELECT * FROM servicio WHERE id = :id")
    fun get(id: Int): LiveData<Servicio>

    @Query("SELECT * FROM servicio WHERE categoria = :categoria")
    fun getPorCategoria(categoria: String): LiveData<List<Servicio>>

    @Query("SELECT * FROM servicio WHERE usuario = :usr")
    fun getPorUsuario(usr: String): LiveData<List<Servicio>>

    @Insert
    fun insertAll(vararg servicios: Servicio)

    @Update
    fun update(servicio: Servicio)

    @Delete
    fun delete(servicio: Servicio)
}