* jblog Implementation summary

   | --- jblog03: Spring MVC(xml config)

   | --- jblog04: Spring MVC(xml config -> java config) - applicationContext, spring-servlet.xml

   | --- jblog05: Spring MVC(xml config -> java config) - web.xml

   | --- jblog06: Spring Framework -> Spring Boot

* DB Schema(MariaDB)
```
-- jblog.`user` definition

CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `join_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- jblog.blog definition

CREATE TABLE `blog` (
  `id` varchar(50) NOT NULL,
  `title` varchar(200) NOT NULL,
  `logo` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_blog_user1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- jblog.category definition

CREATE TABLE `category` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `desc` varchar(200) DEFAULT NULL,
  `reg_date` datetime NOT NULL,
  `blog_id` varchar(50) NOT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_category_blog1` (`blog_id`),
  CONSTRAINT `fk_category_blog1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;


-- jblog.post definition

CREATE TABLE `post` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `contents` text NOT NULL,
  `reg_date` datetime NOT NULL,
  `category_no` int(11) NOT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_post_category1` (`category_no`),
  CONSTRAINT `fk_post_category1` FOREIGN KEY (`category_no`) REFERENCES `category` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
```
