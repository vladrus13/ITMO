delete from Runs
where SessionId in (select s.SessionId from Sessions s where s.TeamId = :TeamId)
