package com.edik.car.api.dao.db;

import com.edik.car.api.dao.db.exception.DbException;
import com.edik.car.api.dao.model.Ad;
import com.edik.car.api.dao.model.Picture;
import com.edik.car.api.dao.model.User;
import com.edik.car.api.dao.model.UserInformation;
import com.edik.car.api.dao.model.UserPhone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HibernateUtil {

    private static final String PROPERTY_FILE_NAME = "hibernate.properties";

    public static SessionFactory getSessionFactory() {
        ServiceRegistry serviceRegistry = configureServiceRegistry();
        return makeSessionFactory(serviceRegistry);
    }

    public static EntityManager getEntityManager() {
        return getSessionFactory().createEntityManager();
    }

    private static SessionFactory makeSessionFactory(ServiceRegistry serviceRegistry) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        metadataSources.addPackage("com.edik.car.api.dao.model");
        metadataSources.addAnnotatedClass(Ad.class);
        metadataSources.addAnnotatedClass(Picture.class);
        metadataSources.addAnnotatedClass(User.class);
        metadataSources.addAnnotatedClass(UserInformation.class);
        metadataSources.addAnnotatedClass(UserPhone.class);

        Metadata metadata = metadataSources.getMetadataBuilder()
            .build();

        return metadata.getSessionFactoryBuilder()
            .build();
    }

    private static ServiceRegistry configureServiceRegistry() {
        return configureServiceRegistry(getProperties());
    }

    private static ServiceRegistry configureServiceRegistry(Properties properties) {
        return new StandardServiceRegistryBuilder().applySettings(properties)
            .build();
    }

    public static Properties getProperties() {
        try {
            Properties properties = new Properties();
            URL propertiesUrl = Thread.currentThread()
                .getContextClassLoader()
                .getResource(PROPERTY_FILE_NAME);
            try (FileInputStream inputStream = new FileInputStream(propertiesUrl.getFile())) {
                properties.load(inputStream);
            }
            return properties;
        } catch (IOException e) {
            log.error("Error during reading hibernate property file", e);
            throw new DbException("Error during reading hibernate property file", e);
        }
    }
}
