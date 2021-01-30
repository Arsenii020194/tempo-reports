# Info
This project helps to load tempo reports by pattern from jira.
# Build
* set file pattern for report, your company jira url, username and password in application.yml
* unix: ./gradlew  build
* windows: gradlew.bat build
# Usage
java -jar tempo-reports-1.0.0.jar

jar has two optional arguments:
* '-p' - project key for report. if not defined, then report will be loaded by all projects.
* '-o' - output file. if not defined, then result will be printed to the console output

# Warning
File pattern depends on your account locale.

# Columns by locale

### RU:
"Ключ запроса","Тема запроса","Часов","Дата работы","Имя пользователя","Полное имя","Период","Ключ аккаунта","Имя аккаунта","Account Lead","Account Category","Account Customer","Имя активности","Компонент","Все компоненты","Имя продукта","Тип запроса","Статус запроса","Ключ проекта","Имя проекта","Epic","Epic Link","Описание работы","Ключ родителя","Автор","Внешние часы","Тарифицируемые часы","Первоначальная оценка запроса","Оставшееся время запроса"
### EN:
"Issue Key","Issue summary","Hours","Work date","Username","Full name","Period","Account Key","Account Name","Account Lead","Account Category","Account Customer","Activity Name","Component","All Components","Version Name","Issue Type","Issue Status","Project Key","Project Name","Epic","Epic Link","Work Description","Parent Key","Reporter","External Hours","Billed Hours","Issue Original Estimate","Issue Remaining Estimate"