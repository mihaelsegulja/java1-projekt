CREATE DATABASE dbRedditRssReader
GO

USE dbRedditRssReader
GO

CREATE TABLE [User] (
  [Id] int IDENTITY(1,1) PRIMARY KEY,
  [Username] nvarchar(50) NOT NULL,
  [PasswordHash] nvarchar(255) NOT NULL,
  [PasswordSalt] nvarchar(255) NOT NULL,
  [DateCreated] datetime2 DEFAULT GETUTCDATE(),
  [UserRoleId] int NOT NULL
)

CREATE TABLE [Author] (
  [Id] int IDENTITY(1,1) PRIMARY KEY,
  [Name] nvarchar(255) NULL,
  [Link] nvarchar(300) NULL
)

CREATE TABLE [Post] (
  [Id] int IDENTITY(1,1) PRIMARY KEY,
  [RedditId] nvarchar(20) NULL,
  [Title] nvarchar(300) NULL,
  [AuthorId] int FOREIGN KEY REFERENCES [Author]([Id]),
  [Link] nvarchar(300) NULL,
  [ThumbnailLink] nvarchar(300) NULL,
  [Content] nvarchar(max) NULL,
  [PublishedDate] nvarchar(30) NULL,
  [UpdatedDate] nvarchar(30) NULL,
  [SubredditName] nvarchar(255) NULL
)

CREATE TABLE [Comment] (
  [Id] int IDENTITY(1,1) PRIMARY KEY,
  [RedditId] nvarchar(20) NULL,
  [Title] nvarchar(300) NULL,
  [AuthorId] int FOREIGN KEY REFERENCES [Author]([Id]),
  [Link] nvarchar(300) NULL,
  [Content] nvarchar(max) NULL,
  [UpdatedDate] nvarchar(30) NULL,
  [SubredditName] nvarchar(255) NULL
)

-- User CRUD

CREATE PROC spCreateUser
  @Username nvarchar(50),
  @PwdHash nvarchar(255),
  @PwdSalt nvarchar(255),
  @UserRoleId int,
  @Id int OUTPUT
AS
BEGIN
  INSERT INTO [User]([Username], [PasswordHash], [PasswordSalt], [UserRoleId])
  VALUES(@Username, @PwdHash, @PwdSalt, @UserRoleId)
  SET @Id = SCOPE_IDENTITY()
END
GO

CREATE PROC spUpdateUser
  @Username nvarchar(50),
  @PwdHash nvarchar(255),
  @PwdSalt nvarchar(255),
  @UserRoleId int,
  @Id int
AS
BEGIN
  UPDATE [User] SET
    Username = @Username,
    PasswordHash = @PwdHash,
    PasswordSalt = @PwdSalt,
    UserRoleId = @UserRoleId
  WHERE Id = @Id
END
GO

CREATE PROC spDeleteUser
  @Id int
AS
BEGIN
  DELETE FROM [User]
  WHERE Id = @Id
END
GO

CREATE PROC spSelectUser
  @Id int
AS
BEGIN
  SELECT * 
  FROM [User]
  WHERE Id = @Id
END
GO

CREATE PROC spSelectUsers
AS
BEGIN
  SELECT * 
  FROM [User]
END
GO

-- User CRUD End

-- Author CRUD

CREATE PROC spCreateAuthor
  @Name nvarchar(255),
  @Link nvarchar(300),
  @Id int OUTPUT
AS
BEGIN
  INSERT INTO [Author]([Name], [Link])
  VALUES(@Name, @Link)
  SET @Id = SCOPE_IDENTITY()
END
GO

CREATE PROC spUpdateAuthor
  @Name nvarchar(255),
  @Link nvarchar(300),
  @Id int
AS
BEGIN
  UPDATE [Author] SET
    Name = @Name,
    Link = @Link
  WHERE Id = @Id
END
GO

CREATE PROC spDeleteAuthor
  @Id int
AS
BEGIN
  DELETE FROM [Author]
  WHERE Id = @Id
END
GO

CREATE PROC spSelectAuthor
  @Id int
AS
BEGIN
  SELECT *
  FROM [Author]
  WHERE Id = @Id
END
GO

CREATE PROC spSelectAuthors
AS
BEGIN
  SELECT *
  FROM [Author]
END
GO

-- Author CRUD End

-- Post CRUD



-- Post CRUD End

-- Comment CRUD



-- Comment CRUD End
