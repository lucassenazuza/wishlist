package com.project.wishilist.repository;

import com.project.wishilist.MongoConfig;
import com.project.wishilist.model.UserEntity;
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
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    static MongodExecutable executable;

    UserEntity user1;
    UserEntity user2;

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
        userRepository.deleteAll();
    }


    @BeforeEach
    void setUp(){
        
        user1 = new UserEntity("lucasz23@email.com","lucas teste","1994-10-20","Brazil","38400022");
        user2 = new UserEntity("luca1d3z@email.com","lucas user 2","1993-10-22","Brazil","38400011");

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void findByUserCodeOrEmailUsingUserCodeSucess() {

        UserEntity foundUser = userRepository.findByEmailOrUserCode(null, user1.getUserCode());

        assertEquals(user1.getEmail(), foundUser.getEmail());
        assertEquals(user1.getUserCode(), foundUser.getUserCode());
        assertEquals(user1.getCountry(), foundUser.getCountry());
        assertEquals(user1.getZipcode(), foundUser.getZipcode());
        assertEquals(user1.getDateBirthday(), foundUser.getDateBirthday());
    }
    @Test
    void findByUserCodeOrEmailUsingemailSucess() {

        UserEntity foundUser = userRepository.findByEmailOrUserCode(user1.getEmail(), null);

        assertEquals(user1.getEmail(), foundUser.getEmail());
        assertEquals(user1.getUserCode(), foundUser.getUserCode());
        assertEquals(user1.getCountry(), foundUser.getCountry());
        assertEquals(user1.getZipcode(), foundUser.getZipcode());
        assertEquals(user1.getDateBirthday(), foundUser.getDateBirthday());
    }

    @Test
    void findByUserCodeOrEmailUsingUserCodeNotFound() {

        UserEntity foundUser = userRepository.findByEmailOrUserCode("pedro123@email.com", null);
        assertNull(foundUser);
    }
    @Test
    void findByUserCodeOrEmailUsingemailNotFound() {

        UserEntity foundUser = userRepository.findByEmailOrUserCode(null, "2ee93f6c-ce4d-44af-ad02-7094feb89f44");
        assertNull(foundUser);
    }
    @Test
    void findByUserCodeOrEmailUsingEanInconsistent() {

        Assertions.assertThrows(IncorrectResultSizeDataAccessException.class, () -> {
            userRepository.findByEmailOrUserCode(user1.getEmail(), user2.getUserCode());
        });
    }
}