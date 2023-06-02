package com.zingos.base_datos

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuario")
    fun getAll(): LiveData<List<Usuario>>

    @Query("SELECT * FROM usuario WHERE usuario = :usr")
    fun get(usr: String): LiveData<Usuario>

    @Insert
    fun insertAll(vararg usuarios: Usuario)

    @Update
    fun update(usuario: Usuario)

    @Delete
    fun delete(usuario: Usuario)
}