
delete from Runs where SessionId in (
  select s.SessionId from Sessions s natural join Contests where ContestName = :ContestName
)
