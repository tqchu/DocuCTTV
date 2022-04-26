package com.ctvv.dao;

import com.ctvv.model.Provider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            if(resultSet.next()){
                return map(resultSet);
            }
            resultSet.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Provider> getAll() {
        String sql = "SELECT * FROM provider";
        List<Provider> providerList = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                providerList.add(map(resultSet));
            }
            resultSet.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return providerList;
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
        Provider provider = new Provider();
        try{
            int providerId = resultSet.getInt("provider_id");
            String providerName = resultSet.getString("provider_name");
            String providerAddress = resultSet.getString("provider_address");
            String phoneNumber = resultSet.getString("phone_number");
            String email = resultSet.getString("email");
            String taxIdNumber = resultSet.getString("tax_id_number");
            provider.setProviderId(providerId);
            provider.setProviderName(providerName);
            provider.setAddress(providerAddress);
            provider.setEmail(email);
            provider.setPhoneNumber(phoneNumber);
            provider.setTaxIdNumber(taxIdNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provider;
    }
    public List<Provider> search(String keyword, String fieldName){
        String sql = "SELECT * FROM provider WHERE "+fieldName+" LIKE ?";
        List<Provider> providerList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,"%"+keyword+"%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                providerList.add(map(resultSet));
            }
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return providerList;
    }
}
