`1.` Запуск контейнеров Docker:
```shell
docker compose up -d
```

`2.` Вывод списка всех контейнеров Docker (включая остановленные):
```shell
docker compose ps -a
```

`3.` Получение списка топиков которые есть в Kafka брокере
```shell
docker exec -ti kafka1 /usr/bin/kafka-topics --list --bootstrap-server localhost:9191
```

`4.` Создание топика "vowels" с тремя партициями
```shell
docker exec -ti kafka1 /usr/bin/kafka-topics --create --topic vowels --partitions 3 --replication-factor 1 --bootstrap-server localhost:9191
```

`5.` Отправляем сообщение "vowels": появляется консоль ввода сообщений, вводим сообщение одно за другим, разделяя Enter и в конце нажимаем в Win ctrl+D (в MacOS: control+C)
```shell
docker exec -ti kafka1 /usr/bin/kafka-console-producer --topic vowels --bootstrap-server kafka1:9191
```

`6.` Получить сообщения
```shell
docker exec -ti kafka1 /usr/bin/kafka-console-consumer --from-beginning --topic vowels --bootstrap-server localhost:9093
```

`7.` Останавливаем контейнеры, удаляем контейнеры, удаляем неиспользуемые тома:
```shell
docker compose stop
docker container prune -f
docker volume prune -f
```