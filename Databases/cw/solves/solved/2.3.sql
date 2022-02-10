
select distinct TeamId from
  Sessions s,
  Runs r
  where r.SessionId = s.SessionId
  and ContestId = :ContestId
  and Accepted = 0
