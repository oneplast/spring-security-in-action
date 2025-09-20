DROP TABLE IF EXISTS workout;

CREATE TABLE workout
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user       VARCHAR(45) NULL,
    start      DATETIME NULL,
    end        DATETIME NULL,
    difficulty INT NULL
);