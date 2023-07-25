package sistema;

import model.Carro;
import model.ValorPermanencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static connection.Conectar.getConnection;

public class SistemaEstacionamento {

    private Statement statatement;

    public SistemaEstacionamento() {
        try {
            statatement = getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserirCarro(Carro carro) {
        String sql = "INSERT INTO tb_carro (nomedono, marcacarro, placa, estado , usuario) VALUES " +
                "('" + carro.getNomeDono() + "', '" + carro.getMarcaDoCarro() + "', '" + carro.getPlaca() +
                "', '" + carro.isEstado() + "', '" + carro.getUsuario() + "')";
        try {
            statatement.executeUpdate(sql);
            System.out.println(" Carro cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserirEntrada(int idCarro, String horaEntrada) {

        LocalTime entrada = LocalTime.parse(horaEntrada);

        String sql = "INSERT INTO tb_estacionamento (carroid, entrada) VALUES " +
                "('" + idCarro + "', '" + horaEntrada + "')";
        try {
            statatement.executeUpdate(sql);
            System.out.println("Hora de entrada cadastrada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void inserirSaida(int idCarro, String horaSaida) {

        LocalTime saida = LocalTime.parse(horaSaida);

        String sql = "UPDATE tb_estacionamento set saida ='" + horaSaida + "'" +
                "WHERE carroid ='" + idCarro + "'" + "AND saida is null";
        try {
            statatement.executeUpdate(sql);
            System.out.println("Hora de saida cadastrada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calcularPermanecia(int idCarro) {
        try {
            String sql = "SELECT entrada, saida FROM tb_estacionamento WHERE carroid = " + idCarro;
            ResultSet resultSet = statatement.executeQuery(sql);
            if (resultSet.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime entrada = LocalTime.parse(resultSet.getString("entrada"), formatter);
                LocalTime saida = LocalTime.parse(resultSet.getString("saida"), formatter);
                // Calculando a duração entre entrada e saída
                Duration duracao = Duration.between(entrada, saida);
                double duracaoEmHoras = duracao.toMinutes() / 60.0; // Convertendo para double
                //System.out.println("Duração em horas: " + duracaoEmHoras);
                return duracaoEmHoras;


            } else {
                System.out.println("Registro não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;


    }

    public void inserirNaTabelaValorPermanencia(int idCarro) {
        double permanencia = calcularPermanecia(idCarro);
        String sql = "UPDATE tb_estacionamento set permanencia ='" + permanencia + "'" +
                "WHERE carroid ='" + idCarro + "'" + "AND permanencia is null";
        try {
            statatement.executeUpdate(sql);
            System.out.println("Permanencia cadastrada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calcularValor(int idCarro){
        double permanencia = calcularPermanecia(idCarro);
        ValorPermanencia valorHora = new ValorPermanencia();
        if (permanencia<=1){
            return valorHora.getValorHora();
        } else if (permanencia<12) {
            double horasCalculadas = Math.ceil((permanencia - 1.0) / 0.5);
            double valorAdicional = horasCalculadas * valorHora.getValorAdicional();
            return 10 + valorAdicional;
        } else {
            return 90.0;

        }

    }
    public void inserirNaTabelaValorPagar(int idCarro) {
        double valor = calcularValor(idCarro);
        String sql = "UPDATE tb_estacionamento set valorpago ='" + valor + "'" +
                "WHERE carroid ='" + idCarro + "'" + "AND valorpago is null";
        try {
            statatement.executeUpdate(sql);
            System.out.println("valor atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inativarCarrosQueSairam(int idCarro){

        String sql = "UPDATE tb_carro set estado='" + false +"'" +
                "WHERE id='" + idCarro + "'";
        try {
            statatement.executeUpdate(sql);
            System.out.println(" carro não está mais no patio ");

        }catch (SQLException e){
            System.out.println("Não foi possivel atualizar o status");
            e.printStackTrace();
        }

    }

    public void consultarTabeladeCarros(){
        String sql = "SELECT * FROM tb_carro";
        try {
            ResultSet resultSet = statatement.executeQuery(sql);
            while (resultSet.next()){
                System.out.println("ID: " + resultSet.getInt("id") + " Nome do Dono: " +
                        resultSet.getString("nomedono") + " Marca do Carro: "
                        + resultSet.getString("marcacarro") +
                        " Placa: " + resultSet.getString("placa") + " estado do carro: " +
                        resultSet.getBoolean("estado"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void consultarTabelaEstacionamento(){
        String sql = "SELECT * FROM tb_estacionamento";
        try {
            ResultSet resultSet = statatement.executeQuery(sql);
            while (resultSet.next()){
                System.out.println("ID: " + resultSet.getInt("id") + " ID carro: " +
                        resultSet.getInt("carroid") + " Horario Entrada: " +
                        resultSet.getString("entrada") + " Horario Saida: "
                        + resultSet.getString("saida") +
                        " Valor a Pagar: " + resultSet.getDouble("valorpago") + " Permanência: " +
                        resultSet.getDouble("permanencia"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


}



