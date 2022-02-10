
select SessionId, count(Letter) as Opened
from (
  select distinct r.SessionId, r.Letter from Runs r
) runs
group by SessionId
