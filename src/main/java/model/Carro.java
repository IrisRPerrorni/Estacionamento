package model;

public class Carro {
    //CREATE TABLE tb_carro(id SERIAL PRIMARY KEY,
    //								nomedono varchar(255),
    //								marcacarro varchar(25),
    //								placa varchar(7),
    //								estado boolean);

    private int idCarro;
    private String nomeDono;
    private String marcaDoCarro;
    private String placa;
    private boolean estado;
    private String usuario;

    public Carro(String nomeDono, String marcaDoCarro, String placa) {
        this.idCarro = idCarro;
        this.nomeDono = nomeDono;
        this.marcaDoCarro = marcaDoCarro;
        this.placa = placa;
        this.estado = true;
        this.usuario = "iris";
    }

    public String getUsuario() {
        return usuario;
    }

    public int getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public String getMarcaDoCarro() {
        return marcaDoCarro;
    }

    public void setMarcaDoCarro(String marcaDoCarro) {
        this.marcaDoCarro = marcaDoCarro;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
