package com.aprendeblockchain.miblockchainenjava.commons.estructuras;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*
 * El pool de transacciones mantiene una colecci�n de transacciones que est�n pendientes de ser incluidas en un bloque y a�adidas a la cadena
 */
public class PoolTransacciones {

	private Set<Transaccion> pool = new HashSet<>();
	
	/**
     * A�adir una transaccion al pool
     * @param transaccion Transaccion a ser a�adida
     * @return true si la transaccion es v�lida y es a�adida al pool
     */
    public synchronized boolean add(Transaccion transaccion) {
        if (transaccion.esValida()) {
        	pool.add(transaccion);
            return true;
        }
        return false;
    }

    /**
     * Eliminar una transaccion del pool
     * @param transaccion Transaccion a eliminar
     */
    public void eliminar(Transaccion transaccion) {
    	pool.remove(transaccion);
    }

    /**
     * Comprobar si el pool contiene una lista de transacciones
     * @param transacciones Lista de transacciones a comprobar
     * @return true si todas las transacciones de la coleccion est�n en el pool
     */
    public boolean contieneTransacciones(Collection<Transaccion> transacciones) {
        return pool.containsAll(transacciones);
    }
}
