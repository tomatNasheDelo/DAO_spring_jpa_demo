persistence.xml — настройки JPA/Hibernate  //  связь между xml файлами 
application-context.xml — настройки Spring beans и транзакций

Да, в этом проекте они связаны, но не как два независимых файла с одинаковой информацией — скорее как две части одной конфигурации JPA + Spring.

Смотрите:

В persistence.xml объявлен persistence unit:

name="ch14"
provider = Hibernate
классы сущностей: Item, Bid
свойства Hibernate: dialect, show_sql, format_sql, hbm2ddl.auto
В application-context.xml создаётся Spring bean:

dataSource
entityManagerFactory
txManager
И там особенно важна строка:

<property name="persistenceUnitName" value="ch14"/>
То есть Spring создаёт EntityManagerFactory через JPA persistence unit с именем ch14, а значит:

Spring берёт настройки JPA из persistence.xml
а datasource/transaction manager уже настраиваются в application-context.xml
Итог:

Да, они являются “парой” в смысле совместной работы.
Но это не “один файл, разрезанный на два”. Это разные уровни конфигурации:
persistence.xml — настройки JPA/Hibernate
application-context.xml — настройки Spring beans и транзакций
Поэтому зависимость есть по имени persistence unit, а не по дублированию данных. Схематически:

persistence.xml (unit ch14) <-----> application-context.xml (entityManagerFactory.persistenceUnitName = "ch14")

Если бы имя не совпадало или persistence.xml не было бы в classpath, Spring не смог бы связать EntityManagerFactory с нужным persistence unit. Но сам datasource и txManager в Spring могут быть настроены и без этого файла, если использовать другую конфигурацию JPA.

