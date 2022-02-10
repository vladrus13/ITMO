select r.RunId, r.SessionId, r.Letter, r.SubmitTime from
  Runs r natural join 
  (select s.SessionId from Sessions s where ContestId = :ContestId) ContestsSessions
  where r.Accepted = 0
