hibernate {
    dialect='org.hibernate.dialect.HSQLDialect'
    connection.driver_class='org.hsqldb.jdbcDriver'
    connection.username='sa'
    connection.password=''
    connection.url='jdbc:hsqldb:.'
    hbm2ddl.auto='create-drop'
    cache.provider_class='org.hibernate.cache.HashtableCacheProvider'
}
