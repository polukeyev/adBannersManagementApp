# adBannersManagementApp
|На русском|In English|
|:----:|:----:|
|Технологии в проекте:|Technologies in project:|
|backend REST API: Java + Spring Boot + Spring Data JPA (Hibernate) + Maven|backend REST API: Java + Spring Boot + Spring Data JPA (Hibernate) + Maven|
|frontend Client: React.js + bootstraps + axios|frontend Client: React.js + bootstraps + axios|
|Database: MySQL|Database: MySQL|
|Version control: GIT|Version control: GIT|
|||
|Для просмотра и изучения проекта, скачайте архив репозитория и распакуйте на вашем устройстве в нужную папку.|For install project download repository archive and unpack it in project folder on ur PC|
|В основной папке проекта есть папка db_backup с дампом базы данных MySQL, извлеките его в новую БД с помощью терминала, MySQL Workbanch или другого клиента.|There is 'db_backup' folder with MySQL DB dump in basic package. Unpack it in new DB with terminal, MySQL Workbench or another client.|
|Также в основной папке есть папка frontend где находится визуальная часть проложения.|Also there is 'frontend' folder in main package, where based UI part of app.|
|||
|Откройте проект из основной папки в вашей IDE и в файле application.properties пропишите корректный значения логина, пароля и URL для доступа к вашей локальной БД. Запустите приложение предварительно пересобрав его Mаven'ом.|Open project in ur IDE and wright in 'application.properties' file correct USERNAME, PASSWORD and URL for ur local DB. Rebuild app with Maven and start it.|
|Откройте проект из папки frontend в вашей IDE и запустите его.|Open project from 'frontend' package in IDE and start it.|
|||
|По умолчанию, порты используемые в проекте: backend: localhost:8080 , frontend: localhost:3000. При изменении порта frontend необходимо поменять его в аннотациях @CrossOrigin классов контроллеров|Default ports in project: backend: localhost:8080 , frontend: localhost:3000. On change frontend's port u also should change port in annotations @CrossOrigin in controllers classes|
----
|Для баннеров и категорий доступны CRUD операции. У каждого баннера есть категория. Нельзя удалить категорию, если в ней есть неудаленные баннеры. Если запросить баннер по категории на странице 'Task request page', вы получите баннер с максимальной ценой из этой категории. Этот баннер будет заблокирован для вас на 24 часа и при повторном обращении по такой же категории с того же IP и User-Agent'а, вы получите следующий незаблокированный баннер с самой высокой ценой. Если баннеров соответствующих критериям не будет, браузер получит статус 204 'no content'.|There are CRUD operations for banners and categories. All banners have a category. U can't delete category, if it has not-deleted banners. When u request banner by category on 'Task request page', u get banner with max price. This banner become banned for ur IP and User-Agent for 24 hours. If u will request banner by same category from same IP and User-Agent, u get next not-banned banner with max price and e.t.c. If there are no banners in this category, browser receive 204 status 'no content'.|
|:----|:----|
