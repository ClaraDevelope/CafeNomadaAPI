package com.ipartek.cafeNomada.apirest.models.entities;

/**
 * Estados del ciclo de vida de un pedido.
 * Usaremos EnumType.STRING en Order para guardar el texto (CREATED, PAID, etc.)
 * en la base de datos, evitando depender del ordinal.
 */
public enum OrderStatus {
    CREATED,
    PAID,
    CANCELLED,
    SHIPPED
}
