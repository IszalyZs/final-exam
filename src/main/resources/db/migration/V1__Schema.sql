DROP TABLE IF EXISTS breed CASCADE;
CREATE TABLE IF NOT EXISTS breed (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    UNIQUE (name)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS dog CASCADE;
CREATE TABLE IF NOT EXISTS dog (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    age_in_month INTEGER NOT NULL,
    chip_code VARCHAR(15) NOT NULL,
    gender ENUM('MALE','FEMALE') NOT NULL,
    breed_id bigint NOT NULL,
    ranch_id bigint NOT NULL,
    UNIQUE (chip_code),
    FOREIGN KEY (breed_id) REFERENCES breed(id),
    FOREIGN KEY (ranch_id) REFERENCES ranch(id)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS ranch CASCADE;
CREATE TABLE IF NOT EXISTS ranch (
   id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   address VARCHAR(200) NOT NULL,
   UNIQUE (address)
) ENGINE=MyISAM;
