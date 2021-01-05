import Lib

-- there is no lib because I don't know about it and use myself-writing libs. Don't ban pls(

toString :: Bool -> String
toString x = if x then "Accepted" else "Wrong answer"

main :: IO ()
main = putStrLn ("nextDay tests...\n" ++ nextDayTests ++ "afterDay tests...\n" ++ afterDayTests ++ "isWeekend tests...\n" ++ isWeekendTests ++ "dayToParty tests...\n" ++ dayToPartyTests) where
    nextDayTests = ("\t" ++ toString(nextDay1) ++ "\n" ++ "\t" ++ toString(nextDay2) ++ "\n") where
        nextDay1 = nextDay(Day "Monday") == Day "Tuesday"
        nextDay2 = nextDay(Day "Sunday") == Day "Monday"
    afterDayTests = ("\t" ++ toString(afterDay1) ++ "\n" ++ "\t" ++ toString(afterDay2) ++ "\n") where
        afterDay1 = (afterDays (Day "Saturday") 2) == Day "Monday"
        afterDay2 = (afterDays (Day "Monday") 49) == Day "Monday"
    isWeekendTests = ("\t" ++ toString(isWeekend1) ++ "\n" ++ "\t" ++ toString(isWeekend2) ++ "\n") where
        isWeekend1 = isWeekend (Day "Sunday")
        isWeekend2 = isWeekend (Day "Wednasday") == False
    dayToPartyTests = ("\t" ++ toString(dayToParty1) ++ "\n" ++ "\t" ++ toString(dayToParty2) ++ "\n") where
        dayToParty1 = dayToParty (Day "Wednasday") == 2
        dayToParty2 = dayToParty (Day "Sunday") == 5