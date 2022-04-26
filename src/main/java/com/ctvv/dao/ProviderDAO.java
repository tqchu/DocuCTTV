package com.ctvv.dao;

import com.ctvv.model.Provider;

import javax.sql.DataSource;
import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProviderDAO extends GenericDAO<Provider>{

    public ProviderDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Provider get(int id) {
        String sql = "SELECT * FROM provider WHERE provider_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String providerName = resultSet.getString("provider_name");
                String providerAddress = resultSet.getString("providerAddress");
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                String taxIdNumber = resultSet.getString("tax_id_number");
                return new Provider(id,providerName,providerAddress,phoneNumber,email,taxIdNumber);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Provider> getAll() {
        return null;
    }

    @Override
    public Provider create(Provider provider) {
        String sql = "INSERT INTO provider(provider_name, provider_address,phone_number,email,tax_id_number) VALUES (?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,provider.getProviderName());
            statement.setString(2,provider.getAddress());
            statement.setString(3,provider.getPhoneNumber());
            statement.setString(4,provider.getEmail());
            statement.setString(5,provider.getTaxIdNumber());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return provider;
    }

    @Override
    public Provider update(Provider provider) {
        String sql = "UPDATE provider SET provider_name = ?, provider_address = ?, phone_number = ?, email = ?, tax_id_number = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,provider.getProviderName());
            statement.setString(2,provider.getAddress());
            statement.setString(3,provider.getPhoneNumber());
            statement.setString(4,provider.getEmail());
            statement.setString(5,provider.getTaxIdNumber());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return provider;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM provider WHERE provider_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Provider map(ResultSet resultSet) {
        return null;
    }

    public Provider findByName(String providerName) {
        String sql = "SELECT * FROM provider WHERE provider_name = ?";
        Provider provider = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {
            ResultSet resultSet;
            statement.setString(1, providerName);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int providerId = resultSet.getInt("provider_id");
                String providerAdress = resultSet.getString("provider_address");
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                String taxIdNumber = resultSet.getString("tax_id_number");
                provider = new Provider(providerId, providerName, providerAdress, phoneNumber, email, taxIdNumber);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provider;
    }

    public Provider findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM provider WHERE phone_number = ?";
        Provider provider = null;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);) {
            ResultSet resultSet;
            statement.setString(1, phoneNumber);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int providerId = resultSet.getInt("provider_id");
                String providerName = resultSet.getString("provider_name");
                String providerAddress = resultSet.getString("provider_address");
                String email = resultSet.getString("email");
                String taxIdNumber = resultSet.getString("tax_id_number");
                provider = new Provider(providerId, providerName, providerAddress, phoneNumber, email, taxIdNumber);
            }
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return provider;
    }

    public Provider findByEmail(String email){
        Provider provider = null;
        return provider;
    }

    public  Provider findByTaxId(String taxId){
        Provider provider = null;
        return provider;
    }
}
