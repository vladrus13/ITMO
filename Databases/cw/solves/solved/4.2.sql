
select TeamId, count(Letter) as Opened
from (
  select distinct s.TeamId, s.ContestId, r.Letter from Runs r natural join Sessions s
) runs
group by TeamId
