package com.zingos.base_datos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "usuario")
class Usuario (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "usuario")
    val usuario: String,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "tipo")
    val tipo: String,
    @ColumnInfo(name = "correo")
    val correo: String,
    @ColumnInfo(name = "telefono")
    val telefono: String
) : Serializable