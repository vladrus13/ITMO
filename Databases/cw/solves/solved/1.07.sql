
select SessionId from Sessions
except
select SessionId from (
  select SessionId, Letter from Sessions natural join Problems
  except
  select SessionId, Letter from Runs
) NotSolved
