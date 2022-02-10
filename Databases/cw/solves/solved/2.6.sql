
select p.ProblemName from Problems p
where not exists (
  select s.SessionId, s.TeamId, s.ContestId, r.Letter from
    Sessions s, Runs r
    where
      r.SessionId = s.SessionId and
      p.Letter = r.Letter and
      p.ContestId = s.ContestId and
      r.Accepted = 1
)
