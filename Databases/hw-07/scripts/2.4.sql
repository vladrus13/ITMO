update Students 
  set GroupId = (select GroupId from Groups where GroupName = :GroupName) 
  where GroupId = (select GroupId from Groups where GroupName = :FromGroupName)
