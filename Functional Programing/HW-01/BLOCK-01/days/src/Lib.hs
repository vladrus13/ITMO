{-# LANGUAGE InstanceSigs #-}

module Lib where

-- | Day
data Day = Day { name :: String }

-- | Equals instance
instance Eq Day where
    (==) :: Day -> Day -> Bool
    a == b = ((name a) == (name b))

-- | Converts from Integer to Day
toEnumDay :: Integer -> Day
toEnumDay n
    | n == 0 = Day "Monday"
    | n == 1 = Day "Tuesday"
    | n == 2 = Day "Wednasday"
    | n == 3 = Day "Thursday"
    | n == 4 = Day "Friday"
    | n == 5 = Day "Saturday"
    | n == 6 = Day "Sunday"

-- | Converts from Day to Integer
fromEnumDay :: Day -> Integer
fromEnumDay day
    | name day == "Monday"    = 0
    | name day == "Tuesday"   = 1
    | name day == "Wednasday" = 2
    | name day == "Thursday"  = 3
    | name day == "Friday"    = 4
    | name day == "Saturday"  = 5
    | name day == "Sunday"    = 6

-- | Get next Day
nextDay :: Day -> Day
nextDay a = toEnumDay (((fromEnumDay a) + 1) `mod` 7)

-- | Get next Day from given
afterDays :: Day -> Integer -> Day
afterDays day next = toEnumDay (((fromEnumDay day) + next) `mod` 7)

-- | Is weekend Day or not
isWeekend :: Day -> Bool
isWeekend day = ((fromEnumDay day) == (fromEnumDay (Day "Sunday")))

-- | Day to Friday
dayToParty :: Day -> Integer
dayToParty day = (((fromEnumDay (Day "Friday")) - (fromEnumDay day)) + 7) `mod` 7