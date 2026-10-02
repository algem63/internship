package internship.config;

import org.aeonbits.owner.Config.Sources;

@Sources("classpath:config.properties")
public interface Config extends org.aeonbits.owner.Config {

    @Key("saucedemo.base_url")
    String saucedemoBaseUrl();

    @Key("saucedemo.username")
    String saucedemoUsername();

    @Key("saucedemo.password")
    String saucedemoPassword();

    @Key("herokuapp.login_url")
    String herokuappLoginUrl();

    @Key("herokuapp.auth_url")
    String herokuappAuthUrl();

    @Key("herokuapp.dynamic-loading_url")
    String herokuappDynamicLoadingUrl();

    @Key("herokuapp.tables_url")
    String herokuappTablesUrl();

    @Key("local.database.url")
    String localDatabaseUrl();

    @Key("local.database.username")
    String localDatabaseUsername();

    @Key("local.database.password")
    String localDatabasePassword();

    @Key("trigger.url")
    String triggerUrl();

    Config INSTANCE = org.aeonbits.owner.ConfigFactory.create(Config.class);
}