package com.project.wishilist.repository;

import com.project.wishilist.MongoConfig;
import com.project.wishilist.model.ProductEntity;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(SpringExtension.class)
@DataMongoTest(excludeAutoConfiguration= {EmbeddedMongoAutoConfiguration.class})
@ContextConfiguration(classes = {MongoConfig.class})
@EnableMongoRepositories
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    static MongodExecutable executable;

    ProductEntity product1;
    ProductEntity product2;

    @BeforeAll
    public static void generalSetup() throws UnknownHostException, IOException {
        int port = 27019;

        MongodStarter starter = MongodStarter.getDefaultInstance();

        MongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.Main.V4_0)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        executable = starter.prepare(mongodConfig);
        executable.start();
    }


    @AfterEach
    public void cleanup(){
        productRepository.deleteAll();
    }

    @BeforeEach
    void setUp(){

        product1 = new ProductEntity("0234567811133", "0234567811133", "200.25");
        product2= new ProductEntity("1234567511133", "1234567811133", "221.25");
        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    void findByProductCodeOrEanUsingProductCodeSucess() {

        ProductEntity foundProduct = productRepository.findByProductCodeOrEan(product1.getProductCode(), null);

        assertEquals(product1.getEan(), foundProduct.getEan());
        assertEquals(product1.getProductCode(), foundProduct.getProductCode());
        assertEquals(product1.getNameProduct(), foundProduct.getNameProduct());
        assertEquals(product1.getPriceProduct(), foundProduct.getPriceProduct());
    }
    @Test
    void findByProductCodeOrEanUsingEanCodeSucess() {

        ProductEntity foundProduct = productRepository.findByProductCodeOrEan(null, "0234567811133");

        assertEquals(product1.getEan(), foundProduct.getEan());
        assertEquals(product1.getProductCode(), foundProduct.getProductCode());
        assertEquals(product1.getNameProduct(), foundProduct.getNameProduct());
        assertEquals(product1.getPriceProduct(), foundProduct.getPriceProduct());
    }

    @Test
    void findByProductCodeOrEanUsingProductNotFound() {

        ProductEntity foundProduct = productRepository.findByProductCodeOrEan("b42161fb-c7f2-4933-87e6-29c444c20352", null);
        assertNull(foundProduct);
    }
    @Test
    void findByProductCodeOrEanUsingEanNotFound() {

        ProductEntity foundProduct = productRepository.findByProductCodeOrEan(null, "0234567811131");
        assertNull(foundProduct);
    }
    @Test
    void findByProductCodeOrEanUsingEanInconsistent() {

        Assertions.assertThrows(IncorrectResultSizeDataAccessException.class, () -> {
            productRepository.findByProductCodeOrEan(product2.getProductCode(), product1.getEan());
        });
    }
}