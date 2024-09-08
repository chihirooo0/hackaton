<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Змейка с Изображением</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
        }

        canvas {
            background-color: #000;
        }
    </style>
</head>
<body>

    <canvas id="snake" width="400" height="400"></canvas>

    <script>
        const canvas = document.getElementById("snake");
        const context = canvas.getContext("2d");

        const box = 32;
        let snake = [];
        snake[0] = { x: 9 * box, y: 10 * box };

        let direction;
        let food = {
            x: Math.floor(Math.random() * 17 + 1) * box,
            y: Math.floor(Math.random() * 15 + 3) * box,
        };

        const foodImage = new Image();
        foodImage.src = "https://avatars.mds.yandex.net/i?id=c6c19df67c9c591aeaf570c72bbc9fc22497ff30-8437558-images-thumbs&n=13"; // Ваша картинка

        let score = 0;

        document.addEventListener("keydown", directionControl);

        function directionControl(event) {
            if (event.keyCode == 37 && direction != "right") direction = "left";
            else if (event.keyCode == 38 && direction != "down") direction = "up";
            else if (event.keyCode == 39 && direction != "left") direction = "right";
            else if (event.keyCode == 40 && direction != "up") direction = "down";
        }

        function collision(head, array) {
            for (let i = 0; i < array.length; i++) {
                if (head.x == array[i].x && head.y == array[i].y) {
                    return true;
                }
            }
            return false;
        }

        function draw() {
            context.clearRect(0, 0, canvas.width, canvas.height);

            // Отрисовка змейки
            for (let i = 0; i < snake.length; i++) {
                context.fillStyle = i == 0 ? "green" : "white";
                context.fillRect(snake[i].x, snake[i].y, box, box);

                context.strokeStyle = "red";
                context.strokeRect(snake[i].x, snake[i].y, box, box);
            }

            // Отрисовка изображения вместо яблока
            context.drawImage(foodImage, food.x, food.y, box, box);

            // Старые координаты головы змейки
            let snakeX = snake[0].x;
            let snakeY = snake[0].y;

            // Направление змейки
            if (direction == "left") snakeX -= box;
            if (direction == "up") snakeY -= box;
            if (direction == "right") snakeX += box;
            if (direction == "down") snakeY += box;

            // Если змейка съедает "яблоко" (ваше изображение)
            if (snakeX == food.x && snakeY == food.y) {
                score++;
                food = {
                    x: Math.floor(Math.random() * 17 + 1) * box,
                    y: Math.floor(Math.random() * 15 + 3) * box,
                };
            } else {
                // Убираем хвост
                snake.pop();
            }

            // Добавляем новую голову
            let newHead = { x: snakeX, y: snakeY };

            // Проверка на столкновение с границами и самой змейкой
            if (
                snakeX < 0 ||
                snakeX > 17 * box ||
                snakeY < 3 * box ||
                snakeY > 17 * box ||
                collision(newHead, snake)
            ) {
                clearInterval(game);
            }

            snake.unshift(newHead);

            // Отображение очков
            context.fillStyle = "white";
            context.font = "45px Arial";
            context.fillText(score, 2 * box, 1.6 * box);
        }

        let game = setInterval(draw, 100);
    </script>

</body>
</html>
