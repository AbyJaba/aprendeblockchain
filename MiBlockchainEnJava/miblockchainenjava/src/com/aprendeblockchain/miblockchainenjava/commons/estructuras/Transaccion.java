package com.aprendeblockchain.miblockchainenjava.commons.estructuras;

import com.aprendeblockchain.miblockchainenjava.commons.utilidades.UtilidadesFirma;
import com.google.common.primitives.Longs;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Date;

/*
 * La informaci�n principal en una transacci�n incluye:
 * 	- Hash de la transacci�n
 * 	- El emisor
 * 	- El destinatario
 * 	- La cantidad a ser transferida
 * 	- El timestamp de cu�ndo fue creada
 * 	- La firma con la clave privada del emisor
 * */

public class Transaccion {

    // Hash de la transacci�n e identificador �nico de �sta
    private byte[] hash;

    // Clave p�blica del emisor de la transacci�n
    private byte[] emisor;

    // Clave p�blica del destinatario de la transacci�n
    private byte[] destinatario;

    // Valor a ser transferido
    private double cantidad;

    // Firma con la clave privada para verificar que la transacci�n fue realmente enviada por el emisor
    private byte[] firma;

    // Timestamp de la creaci�n de la transacci�n en milisegundos desde el 1/1/1970
    private long timestamp;

    public Transaccion() {
    }

    public Transaccion(byte[] emisor, byte[] receptor, double cantidad, byte[] firma) {
        this.emisor = emisor;
        this.destinatario = receptor;
        this.cantidad = cantidad;
        this.firma = firma;
        this.timestamp = System.currentTimeMillis();
        this.hash = calcularHashTransaccion();
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getEmisor() {
        return emisor;
    }

    public void setEmisor(byte[] emisor) {
        this.emisor = emisor;
    }

    public byte[] getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(byte[] destinatario) {
        this.destinatario = destinatario;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public byte[] getFirma() {
        return firma;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * El contenido de la transaccion y que es firmado por el emisor con su clave p�blica
     * @return byte[] Array de bytes representando el contenido de la transaccion
     */
    public byte[] getContenidoTransaccion() {
    	byte[] contenido = ArrayUtils.addAll(String.valueOf(cantidad).getBytes());
    	contenido = ArrayUtils.addAll(contenido, emisor);
    	contenido = ArrayUtils.addAll(contenido, destinatario);
    	contenido = ArrayUtils.addAll(contenido, firma);
    	contenido = ArrayUtils.addAll(contenido, Longs.toByteArray(timestamp));        
        return contenido;
    }

    /**
     * Calcular el hash del contenido de la transacci�n y que pasa a ser el identificar de la transacci�n
     * @return Hash SHA256
     */
    public byte[] calcularHashTransaccion() {
        return DigestUtils.sha256(getContenidoTransaccion());
    }

    /**
     * Comprobar si una transaccion es v�lida
     * @return true si tiene un hash v�lido y la firma es v�lida
     */
    public boolean esValida() {
        // verificar hash
        if (!Arrays.equals(getHash(), calcularHashTransaccion())) {
            return false;
        }
        
        // verificar firma
        try {
            if (!UtilidadesFirma.validarFirma(getContenidoTransaccion(), getFirma(), emisor)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaccion tr = (Transaccion) o;

        return Arrays.equals(hash, tr.hash);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(hash);
    }

    @Override
    public String toString() {
        return "{" + hash + ", " + emisor + ", " + destinatario + ", " + cantidad + ", " + firma + ", " + new Date(timestamp) + "}";
    }
}