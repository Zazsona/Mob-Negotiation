INSERT INTO PowerNegotiationPromptLink (PowerNegotiationPromptId, PowerNegotiationResponseId, PersonalityId, NextPowerNegotiationPromptId, FollowOnSuccess, FollowOnFailure)
VALUES
-- You want... Power? First tell me... what you want do... with life?
    -- Achieve my dreams.
         (900, 0, 1, 901, 1, 0) -- I try... Not succeed... Fun ride though...
            ,(901, null, -1, 925, 1, 1)
            ,(901, null, -1, 950, 1, 1)
        ,(900, 0, 2, 902, 1, 0) -- Wish... you luck.
            ,(902, null, -1, 925, 1, 1)
            ,(902, null, -1, 950, 1, 1)
        ,(900, 0, 3, 903, 1, 0) -- Gurgh... Good luck... You need it.
            ,(903, null, -1, 925, 1, 1)
            ,(903, null, -1, 950, 1, 1)
        ,(900, 0, 4, 904, 1, 0) -- Bugh... I... once... had dreams.
            ,(904, null, -1, 925, 1, 1)
            ,(904, null, -1, 950, 1, 1)

         (900, 0, 1, 905, 0, 1) -- Hargh! You... make me laugh...
        ,(900, 0, 2, 906, 0, 1) -- Guhhh... Why... have? Never succeed...
        ,(900, 0, 3, 907, 0, 1) -- Grah! You... waste time.
        ,(900, 0, 4, 908, 0, 1) -- No one... Buh... have dreams.
    -- Help everyone I meet.
         (900, 1, 1, 909, 1, 0) -- Help...? Hah! You hit me... Funny.
            ,(909, null, -1, 925, 1, 1)
            ,(909, null, -1, 950, 1, 1)
        ,(900, 1, 2, 910, 1, 0) -- You... Ugh... nicer than me.
            ,(910, null, -1, 925, 1, 1)
            ,(910, null, -1, 950, 1, 1)
        ,(900, 1, 3, 911, 1, 0) -- Brr... You... spend life... well.
            ,(911, null, -1, 925, 1, 1)
            ,(911, null, -1, 950, 1, 1)
        ,(900, 1, 4, 912, 1, 0) -- Gurgh... Good... for living...
            ,(912, null, -1, 925, 1, 1)
            ,(912, null, -1, 950, 1, 1)

         (900, 1, 1, 913, 0, 1) -- Hruh. What... waste. Let have... fun.
        ,(900, 1, 2, 914, 0, 1) -- Me... not compare. Hurgh...
        ,(900, 1, 3, 915, 0, 1) -- Lie.
        ,(900, 1, 4, 916, 0, 1) -- Why... bother? Guh...
    -- Die.
         (900, 2, 1, 917, 1, 0) -- Hrah! Join... me. Much fun.
            ,(917, null, -1, 925, 1, 1)
            ,(917, null, -1, 950, 1, 1)
        ,(900, 2, 2, 918, 1, 0) -- Like... Gu... me.
            ,(918, null, -1, 925, 1, 1)
            ,(918, null, -1, 950, 1, 1)
        ,(900, 2, 3, 919, 1, 0) -- Hmuh... I... see.
            ,(919, null, -1, 925, 1, 1)
            ,(919, null, -1, 950, 1, 1)
        ,(900, 2, 4, 920, 1, 0) -- I... same desire.
            ,(920, null, -1, 925, 1, 1)
            ,(920, null, -1, 950, 1, 1)

         (900, 2, 1, 921, 0, 1) -- Let... me help. Hraugh!
        ,(900, 2, 2, 922, 0, 1) -- Guh... That's... sad. Life good.
        ,(900, 2, 3, 923, 0, 1) -- That... waste. Kruh...
        ,(900, 2, 4, 924, 0, 1) -- Guh... Afterlife... bad. Me show you.
-- Surgh... So... what like... to still have freedom... to choose what do with life?
    -- I cherish it.
         (925, 3, 1, 926, 1, 0) -- Hurh... In afterlife... I don't have... to bother.
            ,(926, null, -1, 1125, 1, 1)
        ,(925, 3, 2, 927, 1, 0) -- Ourgh... Once did... too.
            ,(927, null, -1, 1126, 1, 1)
        ,(925, 3, 3, 928, 1, 0) -- Guh... Good.
            ,(928, null, -1, 1127, 1, 1)
        ,(925, 3, 4, 929, 1, 0) -- Gruh... Better... times.
            ,(929, null, -1, 1128, 1, 1)

         (925, 3, 1, 930, 0, 1) -- Hargh... Afterlife... easier... You see... Now.
        ,(925, 3, 2, 931, 0, 1) -- Guh... No... brag.
        ,(925, 3, 3, 932, 0, 1) -- ...Why?
        ,(925, 3, 4, 933, 0, 1) -- Burgh... Forget... cherish.
    -- It defines me.
         (925, 4, 1, 934, 1, 0) -- You... sound like dictionary.
            ,(934, null, -1, 1125, 1, 1)
        ,(925, 4, 2, 935, 1, 0) -- Defines... huurrr...
            ,(935, null, -1, 1126, 1, 1)
        ,(925, 4, 3, 936, 1, 0) -- Buh... You... straightforward. Like me.
            ,(936, null, -1, 1127, 1, 1)
        ,(925, 4, 4, 937, 1, 0) -- Wish... have... choice again... Guh...
            ,(937, null, -1, 1128, 1, 1)

         (925, 4, 1, 938, 0, 1) -- Death... define you. Hargh!
        ,(925, 4, 2, 939, 0, 1) -- Have no... definition... I take yours.
        ,(925, 4, 3, 1030, 0, 1) -- Bad... answer. Gurgh!
        ,(925, 4, 4, 1031, 0, 1) -- Burgh... So simple... not define life.
    -- It's everything.
         (925, 5, 1, 942, 1, 0) -- Hurgh... If... say so.
            ,(942, null, -1, 1125, 1, 1)
        ,(925, 5, 2, 943, 1, 0) -- Hmm...
            ,(943, null, -1, 1126, 1, 1)
        ,(925, 5, 3, 944, 1, 0) -- Guh... Maybe.
            ,(944, null, -1, 1127, 1, 1)
        ,(925, 5, 4, 945, 1, 0) -- Buh... You... understand... life.
            ,(945, null, -1, 1128, 1, 1)

         (925, 5, 1, 946, 0, 1) -- Guh... Lighten up.
        ,(925, 5, 2, 947, 0, 1) -- Buh... I... have nothing... then.
        ,(925, 5, 3, 948, 0, 1) -- Argh... Want answer... not lyrics.
        ,(925, 5, 4, 949, 0, 1) -- Guh... Me... nothing.
-- Buh... So... what... I do with... afterlife?
    -- Join me.
         (950, 6, 1, 951, 1, 0) -- Hurgh? Thanks... But... you... smell funny.
            ,(951, null, -1, 1125, 1, 1)
        ,(950, 6, 2, 952, 1, 0) -- Burgh? Want... me? Can't accept... but thanks.
            ,(952, null, -1, 1126, 1, 1)
        ,(950, 6, 3, 953, 1, 0) -- Not for me... Ugh... but thanks.
            ,(953, null, -1, 1127, 1, 1)
        ,(950, 6, 4, 954, 1, 0) -- Gurr... Thanks... but I slow you down...
            ,(954, null, -1, 1128, 1, 1)

         (950, 6, 1, 955, 0, 1) -- Hurgh. You... ruin my... street cred.
        ,(950, 6, 2, 956, 0, 1) -- Me... too slow.
        ,(950, 6, 3, 957, 0, 1) -- Burgh... No time... for you.
        ,(950, 6, 4, 958, 0, 1) -- Urgh... Too much... effort.
    -- PARTAAAAY!
         (950, 7, 1, 959, 1, 0) -- I... can... gruh... GROOVE!
            ,(959, null, -1, 1125, 1, 1)
        ,(950, 7, 2, 960, 1, 0) -- Sound... fun... Thanks.
            ,(960, null, -1, 1126, 1, 1)
        ,(950, 7, 3, 961, 1, 0) -- Guh... Time... to... lighten up.
            ,(961, null, -1, 1127, 1, 1)
        ,(950, 7, 4, 962, 1, 0) -- Buh... I... just watch.
            ,(962, null, -1, 1128, 1, 1)

         (950, 7, 1, 963, 0, 1) -- Hargh... Not... with you.
        ,(950, 7, 2, 964, 0, 1) -- Not... for me.
        ,(950, 7, 3, 965, 0, 1) -- Buh... You... irritating.
        ,(950, 7, 4, 966, 0, 1) -- Gruh... Joy... Sickening.
    -- Follow your fate.
         (950, 8, 1, 967, 1, 0) -- A fate... for fun. Burguhuh.
            ,(967, null, -1, 1125, 1, 1)
        ,(950, 8, 2, 968, 1, 0) -- Will... do.
            ,(968, null, -1, 1126, 1, 1)
        ,(950, 8, 3, 969, 1, 0) -- ...Not easy...
            ,(969, null, -1, 1127, 1, 1)
        ,(950, 8, 4, 970, 1, 0) -- Gruh... Easy... for some.
            ,(970, null, -1, 1128, 1, 1)

         (950, 8, 1, 971, 0, 1) -- Nurgh... Not... my style.
        ,(950, 8, 2, 972, 0, 1) -- Ugghhh... Fate... is lost.
        ,(950, 8, 3, 973, 0, 1) -- Burghhh... No... such thing.
        ,(950, 8, 4, 974, 0, 1) -- My fate... is to hate...







-- Purgh... Power? How about... propose marriage... first?
    -- Eww, no.
         (975, 9, 1, 976, 1, 0) -- Pargh. I see... I out of... your league.
            ,(976, null, -1, 1000, 1, 1)
            ,(976, null, -1, 1025, 1, 1)
        ,(975, 9, 2, 977, 1, 0) -- Guh... I... wouldn't either.
            ,(977, null, -1, 1000, 1, 1)
            ,(977, null, -1, 1025, 1, 1)
        ,(975, 9, 3, 978, 1, 0) -- Hargh. Saves... divorce papers.
            ,(978, null, -1, 1000, 1, 1)
        ,(975, 9, 4, 979, 1, 0) -- Gruh... Good... choice.
            ,(979, null, -1, 1000, 1, 1)
            ,(979, null, -1, 1025, 1, 1)

         (975, 9, 1, 980, 0, 1) -- Till... death... do us part. Hragh!
        ,(975, 9, 2, 981, 0, 1) -- Graugh... You... awful.
        ,(975, 9, 3, 982, 0, 1) -- We... no business... then.
        ,(975, 9, 4, 983, 0, 1) -- Urgh... You kick... while I down.
    -- You read my mind.
         (975, 10, 1, 984, 1, 0) -- Harr... I'll... set a date.
            ,(984, null, -1, 1000, 1, 1)
            ,(984, null, -1, 1025, 1, 1)
        ,(975, 10, 2, 985, 1, 0) -- I know... juhhh... joke... But thanks.
            ,(985, null, -1, 1000, 1, 1)
            ,(985, null, -1, 1025, 1, 1)
        ,(975, 10, 3, 986, 1, 0) -- Burr... It... only fair.
            ,(986, null, -1, 1000, 1, 1)
            ,(986, null, -1, 1025, 1, 1)
        ,(975, 10, 4, 987, 1, 0) -- Buh... No... mind read...
            ,(987, null, -1, 1000, 1, 1)
            ,(987, null, -1, 1025, 1, 1)

         (975, 10, 1, 988, 0, 1) -- Well... hurgh... I... refuse.
        ,(975, 10, 2, 989, 0, 1) -- Not... true proposal.
        ,(975, 10, 3, 990, 0, 1) -- You... immature.
        ,(975, 10, 4, 991, 0, 1) -- I... not ready... yet.
    -- I don't believe in marriage.
         (975, 11, 1, 992, 1, 0) -- You just believe... Santa Claus?
            ,(992, null, -1, 1000, 1, 1)
            ,(992, null, -1, 1025, 1, 1)
        ,(975, 11, 2, 993, 1, 0) -- Buh... Me... neither.
            ,(993, null, -1, 1000, 1, 1)
            ,(993, null, -1, 1025, 1, 1)
        ,(975, 11, 3, 994, 1, 0) -- Hurgh... Good.
            ,(994, null, -1, 1000, 1, 1)
            ,(994, null, -1, 1025, 1, 1)
        ,(975, 11, 4, 995, 1, 0) -- Hurh... Neither... I.
            ,(995, null, -1, 1000, 1, 1)
            ,(995, null, -1, 1025, 1, 1)

         (975, 11, 1, 996, 0, 1) -- ...Shame. We... funny children.
        ,(975, 11, 2, 997, 0, 1) -- Oh...
        ,(975, 11, 3, 998, 0, 1) -- Furgh... Fool.
        ,(975, 11, 4, 999, 0, 1) -- Ugh... It... only... wish.
-- Either... way. Should confess... sins... now.
    -- No chance.
         (1000, 12, 1, 1001, 1, 0) -- Huhhh... Yeah... same.
            ,(1001, null, -1, 1125, 1, 1)
        ,(1000, 12, 2, 1002, 1, 0) -- Bugh... Keep... to grave. Smart.
            ,(1002, null, -1, 1126, 1, 1)
        ,(1000, 12, 3, 1003, 1, 0) -- Your... truth...
            ,(1003, null, -1, 1127, 1, 1)
        ,(1000, 12, 4, 1004, 1, 0) -- I... guh... have secrets... to.
            ,(1004, null, -1, 1128, 1, 1)

         (1000, 12, 1, 1005, 0, 1) -- No... fun.
        ,(1000, 12, 2, 1006, 0, 1) -- You... bad... person?
        ,(1000, 12, 3, 1007, 0, 1) -- No... trust you.
        ,(1000, 12, 4, 1008, 0, 1) -- You... never find... happiness.
    -- I've been a good little human.
         (1000, 13, 1, 1009, 1, 0) -- If... you say so...
            ,(1009, null, -1, 1125, 1, 1)
        ,(1000, 13, 2, 1010, 1, 0) -- Guhhh... R-really?
            ,(1010, null, -1, 1126, 1, 1)
        ,(1000, 13, 3, 1011, 1, 0) -- Understood...
            ,(1011, null, -1, 1127, 1, 1)
        ,(1000, 13, 4, 1012, 1, 0) -- Buh... World... better place... if true.
            ,(1012, null, -1, 1128, 1, 1)

         (1000, 13, 1, 1013, 0, 1) -- I... not so sure.
        ,(1000, 13, 2, 1014, 0, 1) -- Not... true. I... see.
        ,(1000, 13, 3, 1015, 0, 1) -- You... lie.
        ,(1000, 13, 4, 1016, 0, 1) -- I... not... so good.
    -- I have a dark past.
         (1000, 14, 1, 1017, 1, 0) -- Bugh! What... worst you do? Forget to... say please?
            ,(1017, null, -1, 1125, 1, 1)
        ,(1000, 14, 2, 1018, 1, 0) -- Buh... We all... have history.
            ,(1018, null, -1, 1126, 1, 1)
        ,(1000, 14, 3, 1019, 1, 0) -- I... understand.
            ,(1019, null, -1, 1127, 1, 1)
        ,(1000, 14, 4, 1020, 1, 0) -- I... same.
            ,(1020, null, -1, 1128, 1, 1)

         (1000, 14, 1, 1021, 0, 1) -- Ow... the edge.
        ,(1000, 14, 2, 1022, 0, 1) -- You... bad. Gargh!
        ,(1000, 14, 3, 1023, 0, 1) -- Guhhhh... Yeah... right.
        ,(1000, 14, 4, 1024, 0, 1) -- Mine... darker.
-- Come on... what vows... you make?
    -- Vows lead to disappointment.
         (1025, 15, 1, 1026, 1, 0) -- Gurhhhh... I... take that... as vow.
            ,(1026, null, -1, 1125, 1, 1)
        ,(1025, 15, 2, 1027, 1, 0) -- Thanks... for spare me... sadness.
            ,(1027, null, -1, 1126, 1, 1)
        ,(1025, 15, 3, 1028, 1, 0) -- You... right.
            ,(1028, null, -1, 1127, 1, 1)
        ,(1025, 15, 4, 1029, 1, 0) -- Always... I... hurt before.
            ,(1029, null, -1, 1128, 1, 1)

         (1025, 15, 1, 1030, 0, 1) -- Buh... Like... that... answer.
        ,(1025, 15, 2, 1031, 0, 1) -- You... lack... heart.
        ,(1025, 15, 3, 1032, 0, 1) -- You... hopeless... disappointment.
        ,(1025, 15, 4, 1033, 0, 1) -- Buh... You... not care.
    -- I'll cherish you forever.
         (1025, 16, 1, 1034, 1, 0) -- You... make me... blush.
            ,(1034, null, -1, 1125, 1, 1)
        ,(1025, 16, 2, 1035, 1, 0) -- Fuhhh.... Thanks.
            ,(1035, null, -1, 1126, 1, 1)
        ,(1025, 16, 3, 1036, 1, 0) -- Not... quite feel same... But okay.
            ,(1036, null, -1, 1127, 1, 1)
        ,(1025, 16, 4, 1037, 1, 0) -- Appreciate... the idea. I... not lovable.
            ,(1037, null, -1, 1128, 1, 1)

         (1025, 16, 1, 1038, 0, 1) -- I... cherish killing you.
        ,(1025, 16, 2, 1039, 0, 1) -- ...Guh. Don't... lie.
        ,(1025, 16, 3, 1040, 0, 1) -- That... impossible... Fool.
        ,(1025, 16, 4, 1041, 0, 1) -- I... incapable... of love.
    -- I vow to kill you.
         (1025, 17, 1, 1042, 1, 0) -- Hargh! You... too weak... Good... joke.
            ,(1042, null, -1, 1125, 1, 1)
        ,(1025, 17, 2, 1043, 1, 0) -- I... appreciate... death... later.
            ,(1043, null, -1, 1126, 1, 1)
        ,(1025, 17, 3, 1044, 1, 0) -- You... actually commit.
            ,(1044, null, -1, 1127, 1, 1)
        ,(1025, 17, 4, 1045, 1, 0) -- End... my suffering... I... remember this.
            ,(1045, null, -1, 1128, 1, 1)

         (1025, 17, 1, 1046, 0, 1) -- I... kill you... FIRST!
        ,(1025, 17, 2, 1047, 0, 1) -- Guh... So... be it.
        ,(1025, 17, 3, 1048, 0, 1) -- ...Fool.
        ,(1025, 17, 4, 1049, 0, 1) -- Guh... Go ahead...




-- Power...? Do... I see... hand shaking? You... okay?
    -- It's a bit chilly...
         (1050, 18, 1, 1051, 1, 0) -- That just... because I cool.
            ,(1051, null, -1, 1075, 1, 1)
            ,(1051, null, -1, 1100, 1, 1)
        ,(1050, 18, 2, 1052, 1, 0) -- Brrr... I... cold too.
            ,(1052, null, -1, 1100, 1, 1)
        ,(1050, 18, 3, 1053, 1, 0) -- Brrr... Should... layer up.
            ,(1053, null, -1, 1075, 1, 1)
            ,(1053, null, -1, 1100, 1, 1)
        ,(1050, 18, 4, 1054, 1, 0) -- Death... guh... is always... cold.
            ,(1054, null, -1, 1075, 1, 1)
            ,(1054, null, -1, 1100, 1, 1)

         (1050, 18, 1, 1055, 0, 1) -- You... wimp.
        ,(1050, 18, 2, 1056, 0, 1) -- Buh... You just... want leave.
        ,(1050, 18, 3, 1057, 0, 1) -- No... it not.
        ,(1050, 18, 4, 1058, 0, 1) -- You... don't know... cold.
    -- I'm just excited to talk!
         (1050, 19, 1, 1059, 1, 0) -- I... am... pretty charismatic.
            ,(1059, null, -1, 1075, 1, 1)
            ,(1059, null, -1, 1100, 1, 1)
        ,(1050, 19, 2, 1060, 1, 0) -- To... Talk... to me?
            ,(1060, null, -1, 1075, 1, 1)
            ,(1060, null, -1, 1100, 1, 1)
        ,(1050, 19, 3, 1061, 1, 0) -- Buh... You... calm down... then.
            ,(1061, null, -1, 1075, 1, 1)
            ,(1061, null, -1, 1100, 1, 1)
        ,(1050, 19, 4, 1062, 1, 0) -- Buh... Don't... be.
            ,(1062, null, -1, 1075, 1, 1)
            ,(1062, null, -1, 1100, 1, 1)

         (1050, 19, 1, 1063, 0, 1) -- I... NOT!
        ,(1050, 19, 2, 1064, 0, 1) -- I... not worth... excitement.
        ,(1050, 19, 3, 1065, 0, 1) -- Hurgh... You... childish.
        ,(1050, 19, 4, 1066, 0, 1) -- I... not mood.
    -- Jazz hands!
         (1050, 20, 1, 1067, 1, 0) -- Grrrroovy!
            ,(1067, null, -1, 1075, 1, 1)
            ,(1067, null, -1, 1100, 1, 1)
        ,(1050, 20, 2, 1068, 1, 0) -- You... want dance?
            ,(1068, null, -1, 1075, 1, 1)
            ,(1068, null, -1, 1100, 1, 1)
        ,(1050, 20, 3, 1069, 1, 0) -- Guuhhh... Not... my style... but... must... admit... you good.
            ,(1069, null, -1, 1075, 1, 1)
            ,(1069, null, -1, 1100, 1, 1)
        ,(1050, 20, 4, 1070, 1, 0) -- Nice... save.
            ,(1070, null, -1, 1075, 1, 1)
            ,(1070, null, -1, 1100, 1, 1)

         (1050, 20, 1, 1071, 0, 1) -- You... dare challenge... jazz hand master?
        ,(1050, 20, 2, 1072, 0, 1) -- Buh... I... just wanted... to know... if you... okay.
        ,(1050, 20, 3, 1073, 0, 1) -- HRUAGH!
        ,(1050, 20, 4, 1074, 0, 1) -- Graugh... Go... away.
-- You... ever... get shaky... when kid too?
    -- When I was scared...
         (1075, 21, 1, 1076, 1, 0) -- Hurh... You... still... scared now!
            ,(1076, null, -1, 1125, 1, 1)
        ,(1075, 21, 2, 1077, 1, 0) -- Me... too.
            ,(1077, null, -1, 1126, 1, 1)
        ,(1075, 21, 3, 1078, 1, 0) -- You... look scared... now. No try... hide it.
            ,(1078, null, -1, 1127, 1, 1)
        ,(1075, 21, 4, 1079, 1, 0) -- Buh... I... scared... lots.
            ,(1079, null, -1, 1128, 1, 1)

         (1075, 21, 1, 1080, 0, 1) -- Ha-hargh! You... wimp.
        ,(1075, 21, 2, 1081, 0, 1) -- Buh... You... look scared... of me... now.
        ,(1075, 21, 3, 1082, 0, 1) -- Gurgh. I... no time... for weak wimp.
        ,(1075, 21, 4, 1083, 0, 1) -- Wish... scared. I... no feeling.
    -- Only after coffee.
         (1075, 22, 1, 1084, 1, 0) -- Ha-hurgh... Bet... you shake... on toilet too.
            ,(1084, null, -1, 1125, 1, 1)
        ,(1075, 22, 2, 1085, 1, 0) -- Ourgh... Always... make me... shake.
            ,(1085, null, -1, 1126, 1, 1)
        ,(1075, 22, 3, 1086, 1, 0) -- Bugh... I... also... coffee addict. That why... not rest... after death.
            ,(1086, null, -1, 1127, 1, 1)
        ,(1075, 22, 4, 1087, 1, 0) -- Ugh... Even child... need coffee... for busy life...
            ,(1087, null, -1, 1128, 1, 1)

         (1075, 22, 1, 1088, 0, 1) -- Boring... child... have coffee.
        ,(1075, 22, 2, 1089, 0, 1) -- Baugh! Why... you... have coffee... as kid?
        ,(1075, 22, 3, 1090, 0, 1) -- Graugh... Child... coffee... bad.
        ,(1075, 22, 4, 1091, 0, 1) -- Not... joke question.
    -- Once...
         (1075, 23, 1, 1092, 1, 0) -- Huhhh... You'd... still shake... now!
            ,(1092, null, -1, 1125, 1, 1)
        ,(1075, 23, 2, 1093, 1, 0) -- I... no pry.
            ,(1093, null, -1, 1126, 1, 1)
        ,(1075, 23, 3, 1094, 1, 0) -- Buh... Prepare you... Harder time... come yet.
            ,(1094, null, -1, 1127, 1, 1)
        ,(1075, 23, 4, 1095, 1, 0) -- Guh... We both... have suffered.
            ,(1095, null, -1, 1128, 1, 1)

         (1075, 23, 1, 1096, 0, 1) -- I... make that twice... Now.
        ,(1075, 23, 2, 1097, 0, 1) -- I... don't... Ugh... believe.
        ,(1075, 23, 3, 1098, 0, 1) -- Ugh... Weak...
        ,(1075, 23, 4, 1099, 0, 1) -- You... not suffered enough... like me.
-- Speaking of... shaky... hands... You... any good... at sewing? I... need clothes... fixing.
    -- They're not too bad.
         (1100, 24, 1, 1101, 1, 0) -- Hagh! They... certainly... more style... than yours.
            ,(1101, null, -1, 1125, 1, 1)
        ,(1100, 24, 2, 1102, 1, 0) -- Thanks... I... feel better.
            ,(1102, null, -1, 1126, 1, 1)
        ,(1100, 24, 3, 1103, 1, 0) -- Hoogh... You... should try... wearing them.
            ,(1103, null, -1, 1127, 1, 1)
        ,(1100, 24, 4, 1104, 1, 0) -- Maybe... compared to... goorgh... rest of life.
            ,(1104, null, -1, 1128, 1, 1)

         (1100, 24, 1, 1105, 0, 1) -- You... try on... then!
        ,(1100, 24, 2, 1106, 0, 1) -- Uhn... You just... think you better... than me.
        ,(1100, 24, 3, 1107, 0, 1) -- Ugh... You just... too lazy.
        ,(1100, 24, 4, 1108, 0, 1) -- No... they're... bad.... like me...
    -- My hands are literal blocks.
         (1100, 25, 1, 1109, 1, 0) -- ...Wow. Mine... too. Suh... Strange... isn't it?
            ,(1109, null, -1, 1125, 1, 1)
        ,(1100, 25, 2, 1110, 1, 0) -- We... not so... different.
            ,(1110, null, -1, 1126, 1, 1)
        ,(1100, 25, 3, 1111, 1, 0) -- ...No kidding. Mine... too.
            ,(1111, null, -1, 1127, 1, 1)
        ,(1100, 25, 4, 1112, 1, 0) -- Gruh... At least... you can put down.
            ,(1112, null, -1, 1128, 1, 1)

         (1100, 25, 1, 1113, 0, 1) -- Well... done... Sherlock Bones...
        ,(1100, 25, 2, 1114, 0, 1) -- ...You think... I stupid?
        ,(1100, 25, 3, 1115, 0, 1) -- Argh... I... know. Don't... care...
        ,(1100, 25, 4, 1116, 0, 1) -- Murgh... Don't... toy... with me.
    -- I'm good when I'm paid.
         (1100, 26, 1, 1117, 1, 0) -- Guess... you no take... rotten flesh?
            ,(1117, null, -1, 1125, 1, 1)
        ,(1100, 26, 2, 1118, 1, 0) -- I... gruh... think about it...
            ,(1118, null, -1, 1126, 1, 1)
        ,(1100, 26, 3, 1119, 1, 0) -- Harh... You... better be.
            ,(1119, null, -1, 1127, 1, 1)
        ,(1100, 26, 4, 1120, 1, 0) -- Hurh... Fair's... fair.
            ,(1120, null, -1, 1128, 1, 1)

         (1100, 26, 1, 1121, 0, 1) -- With... that tatty shirt? I... see... this stitch up.
        ,(1100, 26, 2, 1122, 0, 1) -- Hooh... Was hoping... kind.
        ,(1100, 26, 3, 1123, 0, 1) -- I... not fall... for con.
        ,(1100, 26, 4, 1124, 0, 1) -- You... greedy. That... lead you... to darkness... and demise.
;