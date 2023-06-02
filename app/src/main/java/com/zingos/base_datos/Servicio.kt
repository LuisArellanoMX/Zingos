package com.zingos.base_datos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "servicio",
    foreignKeys = [
        ForeignKey(entity = Usuario::class, parentColumns = ["usuario"], childColumns = ["usuario"])
    ]
)
class Servicio(
    @ColumnInfo(name = "nombre_servicio")
    val nombre_servicio: String,
    @ColumnInfo(name = "categoria")
    val categoria: String,
    @ColumnInfo(name = "horario")
    val horario: String,
    @ColumnInfo(name = "ubicacion")
    val ubicacion: String,
    @ColumnInfo(name = "descripcion")
    val descripcion: String,
    @ColumnInfo(name = "usuario")
    val usuario: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
): Serializable