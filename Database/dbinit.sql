CREATE DATABASE dbRedditRssReader
GO

USE dbRedditRssReader
GO

CREATE TABLE [User] (
  [Id] int IDENTITY(1,1) PRIMARY KEY,
  [Username] nvarchar(50) UNIQUE NOT NULL,
  [PasswordHash] nvarchar(255) NOT NULL,
  [PasswordSalt] nvarchar(255) NOT NULL,
  [UserRoleId] int NOT NULL
)
GO

CREATE TABLE [Author] (
  [Id] int IDENTITY(1,1) PRIMARY KEY,
  [Name] nvarchar(255) NULL,
  [Link] nvarchar(300) NULL
)
GO

CREATE TABLE [Post] (
  [Id] int IDENTITY(1,1) PRIMARY KEY,
  [RedditId] nvarchar(20) NULL,
  [Title] nvarchar(300) NULL,
  [AuthorId] int NULL FOREIGN KEY REFERENCES [Author]([Id]),
  [Link] nvarchar(300) NULL,
  [ThumbnailLink] nvarchar(300) NULL,
  [Content] nvarchar(max) NULL,
  [PublishedDate] nvarchar(30) NULL,
  [UpdatedDate] nvarchar(30) NULL,
  [SubredditName] nvarchar(255) NULL
)
GO

CREATE TABLE [Comment] (
  [Id] int IDENTITY(1,1) PRIMARY KEY,
  [RedditId] nvarchar(20) NULL,
  [Title] nvarchar(300) NULL,
  [AuthorId] int NULL FOREIGN KEY REFERENCES [Author]([Id]),
  [Link] nvarchar(300) NULL,
  [Content] nvarchar(max) NULL,
  [UpdatedDate] nvarchar(30) NULL,
  [SubredditName] nvarchar(255) NULL
)
GO

-- User CRUD

CREATE PROC spCreateUser
  @Username nvarchar(50),
  @PasswordHash nvarchar(255),
  @PasswordSalt nvarchar(255),
  @UserRoleId int,
  @Id int OUTPUT
AS
BEGIN
  INSERT INTO [User] (
    [Username], 
    [PasswordHash], 
    [PasswordSalt], 
    [UserRoleId]
  )
  VALUES (
    @Username, 
    @PasswordHash, 
    @PasswordSalt, 
    @UserRoleId
  )
  SET @Id = SCOPE_IDENTITY()
END
GO

CREATE PROC spUpdateUser
  @Username nvarchar(50),
  @PasswordHash nvarchar(255),
  @PasswordSalt nvarchar(255),
  @UserRoleId int,
  @Id int
AS
BEGIN
  UPDATE [User] SET
    Username = @Username,
    PasswordHash = @PasswordHash,
    PasswordSalt = @PasswordSalt,
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

CREATE PROC spCreatePost
  @RedditId nvarchar(20),
  @Title nvarchar(300),
  @AuthorName nvarchar(255),
  @AuthorLink nvarchar(300),
  @Link nvarchar(300),
  @ThumbnailLink nvarchar(300),
  @Content nvarchar(max),
  @PublishedDate nvarchar(30),
  @UpdatedDate nvarchar(30),
  @SubredditName nvarchar(255),
  @Id int OUTPUT
AS
BEGIN
  DECLARE @AuthorId int
  
  SELECT @AuthorId = Id FROM [Author] WHERE [Name] = @AuthorName
  
  IF @AuthorId IS NULL
  BEGIN
    EXEC spCreateAuthor @Name = @AuthorName, @Link = @AuthorLink, @Id = @AuthorId OUTPUT
  END
  
  INSERT INTO [Post] (
    [RedditId],
    [Title],
    [AuthorId],
    [Link],
    [ThumbnailLink],
    [Content],
    [PublishedDate],
    [UpdatedDate],
    [SubredditName]
  )
  VALUES (
    @RedditId,
    @Title,
    @AuthorId,
    @Link,
    @ThumbnailLink,
    @Content,
    @PublishedDate,
    @UpdatedDate,
    @SubredditName
  )
  SET @Id = SCOPE_IDENTITY()
END
GO

CREATE PROC spUpdatePost
  @Id int,
  @RedditId nvarchar(20),
  @Title nvarchar(300),
  @AuthorId int,
  @Link nvarchar(300),
  @ThumbnailLink nvarchar(300),
  @Content nvarchar(max),
  @PublishedDate nvarchar(30),
  @UpdatedDate nvarchar(30),
  @SubredditName nvarchar(255)
AS
BEGIN
  UPDATE [Post] SET
    RedditId = @RedditId,
    Title = @Title,
    AuthorId = @AuthorId,
    Link = @Link,
    ThumbnailLink = @ThumbnailLink,
    Content = @Content,
    PublishedDate = @PublishedDate,
    UpdatedDate = @UpdatedDate,
    SubredditName = @SubredditName
  WHERE Id = @Id
END
GO

CREATE PROC spDeletePost
  @Id int
AS
BEGIN
  DELETE FROM [Post]
  WHERE Id = @Id
END
GO

CREATE PROC spSelectPost
  @Id int
AS
BEGIN
  SELECT p.*, a.Id AS AuthorId, a.Name AS AuthorName, a.Link AS AuthorLink
  FROM [Post] p
  LEFT JOIN [Author] a ON p.AuthorId = a.Id
  WHERE p.Id = @Id
END
GO

CREATE PROC spSelectPosts
AS
BEGIN
  SELECT p.*, a.Id AS AuthorId, a.Name AS AuthorName, a.Link AS AuthorLink
  FROM [Post] p
  LEFT JOIN [Author] a ON p.AuthorId = a.Id
END
GO

CREATE PROC spSelectPostsBySubreddit
  @SubredditName nvarchar(255)
AS
BEGIN
  SELECT p.*, a.Id AS AuthorId, a.Name AS AuthorName, a.Link AS AuthorLink
  FROM [Post] p
  LEFT JOIN [Author] a ON p.AuthorId = a.Id
  WHERE p.SubredditName = @SubredditName
END
GO

-- Post CRUD End

-- Comment CRUD

CREATE PROC spCreateComment
  @RedditId nvarchar(20),
  @Title nvarchar(300),
  @AuthorName nvarchar(255),
  @AuthorLink nvarchar(300),
  @Link nvarchar(300),
  @Content nvarchar(max),
  @UpdatedDate nvarchar(30),
  @SubredditName nvarchar(255),
  @Id int OUTPUT
AS
BEGIN
  DECLARE @AuthorId int
  
  SELECT @AuthorId = Id FROM [Author] WHERE [Name] = @AuthorName
  
  IF @AuthorId IS NULL
  BEGIN
    EXEC spCreateAuthor @Name = @AuthorName, @Link = @AuthorLink, @Id = @AuthorId OUTPUT
  END
  
  INSERT INTO [Comment] (
    [RedditId],
    [Title],
    [AuthorId],
    [Link],
    [Content],
    [UpdatedDate],
    [SubredditName]
  )
  VALUES (
    @RedditId,
    @Title,
    @AuthorId,
    @Link,
    @Content,
    @UpdatedDate,
    @SubredditName
  )
  SET @Id = SCOPE_IDENTITY()
END
GO

CREATE PROC spUpdateComment
  @Id int,
  @RedditId nvarchar(20),
  @Title nvarchar(300),
  @AuthorId int,
  @Link nvarchar(300),
  @Content nvarchar(max),
  @UpdatedDate nvarchar(30),
  @SubredditName nvarchar(255)
AS
BEGIN
  UPDATE [Comment] SET
    RedditId = @RedditId,
    Title = @Title,
    AuthorId = @AuthorId,
    Link = @Link,
    Content = @Content,
    UpdatedDate = @UpdatedDate,
    SubredditName = @SubredditName
  WHERE Id = @Id
END
GO

CREATE PROC spDeleteComment
  @Id int
AS
BEGIN
  DELETE FROM [Comment]
  WHERE Id = @Id
END
GO

CREATE PROC spSelectComment
  @Id int
AS
BEGIN
  SELECT c.*, a.Id AS AuthorId, a.Name AS AuthorName, a.Link AS AuthorLink
  FROM [Comment] c
  LEFT JOIN [Author] a ON c.AuthorId = a.Id
  WHERE c.Id = @Id
END
GO

CREATE PROC spSelectComments
AS
BEGIN
  SELECT c.*, a.Id AS AuthorId, a.Name AS AuthorName, a.Link AS AuthorLink
  FROM [Comment] c
  LEFT JOIN [Author] a ON c.AuthorId = a.Id
END
GO

CREATE PROC spSelectCommentsByPost
  @PostId int
AS
BEGIN
  SELECT c.*, a.Id AS AuthorId, a.Name AS AuthorName, a.Link AS AuthorLink
  FROM [Comment] c
  LEFT JOIN [Author] a ON c.AuthorId = a.Id
  WHERE c.RedditId IN (
    SELECT RedditId FROM [Post] WHERE Id = @PostId
  )
END
GO

-- Comment CRUD End

CREATE PROC spDeleteAll
AS
BEGIN
  DELETE FROM [Comment]
  DELETE FROM [Post]
  DELETE FROM [Author]

  DBCC CHECKIDENT ('[Author]', RESEED, 0)
  DBCC CHECKIDENT ('[Comment]', RESEED, 0)
  DBCC CHECKIDENT ('[Post]', RESEED, 0)
END
GO

-- seed

declare @tempId int
exec spCreateUser 'Admin', 'o7jETcZI1qQS2L51Yj8KfSJpoiqEHJVEG5rRfoWAMzs=', 'Dir0wLvK1M46ZEwM/CIdrA==', 100, @tempId
GO
