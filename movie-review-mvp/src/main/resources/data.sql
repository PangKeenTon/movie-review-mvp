-- Thêm thể loại phim
INSERT INTO GENRES (name) VALUES ('Hành Động');
INSERT INTO GENRES (name) VALUES ('Phiêu Lưu');
INSERT INTO GENRES (name) VALUES ('Hoạt Hình');
INSERT INTO GENRES (name) VALUES ('Kinh Dị');
INSERT INTO GENRES (name) VALUES ('Khoa Học Viễn Tưởng');

-- Thêm các vai trò cơ bản vào hệ thống
INSERT INTO ROLES (name) SELECT 'ROLE_USER' WHERE NOT EXISTS (SELECT 1 FROM ROLES WHERE name = 'ROLE_USER');
INSERT INTO ROLES (name) SELECT 'ROLE_ADMIN' WHERE NOT EXISTS (SELECT 1 FROM ROLES WHERE name = 'ROLE_ADMIN');
INSERT INTO ROLES (name) SELECT 'ROLE_CRITIC' WHERE NOT EXISTS (SELECT 1 FROM ROLES WHERE name = 'ROLE_CRITIC');

-- Thêm phim
-- Lưu ý: poster_url có thể là đường dẫn tương đối đến thư mục static/images hoặc URL đầy đủ
INSERT INTO MOVIES (title, description, release_year, poster_url, director_name) VALUES
('Inception', 'Một tên trộm chuyên đánh cắp thông tin bằng cách xâm nhập vào tiềm thức của người khác.', 2010, '/images/posters/inception.jpg', 'Christopher Nolan'),
('The Avengers', 'Một nhóm siêu anh hùng tập hợp lại để cứu thế giới khỏi hiểm họa.', 2012, '/images/posters/avengers.jpg', 'Joss Whedon'),
('Spider-Man: Into the Spider-Verse', 'Miles Morales trở thành Spider-Man của vũ trụ mình và tham gia cùng các Spider-People từ các vũ trụ khác.', 2018, '/images/posters/spiderverse.jpg', 'Bob Persichetti, Peter Ramsey, Rodney Rothman'),
('A Quiet Place', 'Một gia đình phải sống trong im lặng để tránh những sinh vật săn mồi theo âm thanh.', 2018, '/images/posters/aquietplace.jpg', 'John Krasinski');

-- Liên kết phim với thể loại (Bảng nối movie_genres)
-- Giả sử ID của Inception là 1, The Avengers là 2, Spider-Verse là 3, A Quiet Place là 4
-- Giả sử ID của Hành Động là 1, Phiêu Lưu là 2, Hoạt Hình là 3, Kinh Dị là 4, Khoa Học Viễn Tưởng là 5

-- Inception: Hành Động (1), Khoa Học Viễn Tưởng (5)
INSERT INTO MOVIE_GENRES (movie_id, genre_id) VALUES (1, 1);
INSERT INTO MOVIE_GENRES (movie_id, genre_id) VALUES (1, 5);

-- The Avengers: Hành Động (1), Phiêu Lưu (2), Khoa Học Viễn Tưởng (5)
INSERT INTO MOVIE_GENRES (movie_id, genre_id) VALUES (2, 1);
INSERT INTO MOVIE_GENRES (movie_id, genre_id) VALUES (2, 2);
INSERT INTO MOVIE_GENRES (movie_id, genre_id) VALUES (2, 5);

-- Spider-Man: Into the Spider-Verse: Hoạt Hình (3), Hành Động (1), Phiêu Lưu (2)
INSERT INTO MOVIE_GENRES (movie_id, genre_id) VALUES (3, 3);
INSERT INTO MOVIE_GENRES (movie_id, genre_id) VALUES (3, 1);
INSERT INTO MOVIE_GENRES (movie_id, genre_id) VALUES (3, 2);

-- A Quiet Place: Kinh Dị (4)
INSERT INTO MOVIE_GENRES (movie_id, genre_id) VALUES (4, 4);