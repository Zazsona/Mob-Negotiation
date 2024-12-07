INSERT INTO MoneyNegotiationPrompt (MoneyNegotiationPromptId, ScriptLineId, PersonalityId, CanBeInitialOffer, CanBeRevisedOffer, CanBeRepeatOffer, CanBeRejection, CanBeAcceptance)
VALUES
      -- Creeper
      (0, 600, 1, 1, 0, 0, 0, 0) -- "Here'sss an explosssive offer for you then, how'sss %MONEY%?"
     ,(1, 601, 2, 1, 0, 0, 0, 0) -- "Alright... Take thisss %MONEY%."
     ,(2, 602, 3, 1, 0, 0, 0, 0) -- "I have to pay you to go away? Fine. Here'sss %MONEY%."
     ,(3, 603, 4, 1, 0, 0, 0, 0) -- "My life'sss only worth %MONEY%..."

     ,(4, 604, 1, 0, 1, 0, 0, 0) -- "Ssso I'm not going away that easy? Alright, here'sss %MONEY%."
     ,(5, 605, 2, 0, 1, 0, 0, 0) -- "You desssire more? Here's %MONEY%."
     ,(6, 606, 3, 0, 1, 0, 0, 0) -- "...Oh? Isss that ssstill not enough? You're insufferable. Take thisss %MONEY%."
     ,(7, 607, 4, 0, 1, 0, 0, 0) -- "No richesss for Creepersss... Here'sss %MONEY%."

     ,(8, 608, 1, 0, 1, 0, 0, 0) -- "Alright, friendsss and family ssspecial: %MONEY%."
     ,(9, 609, 2, 0, 1, 0, 0, 0) -- "I'll be in sssoo much trouble giving you %MONEY%..."
     ,(10, 610, 3, 0, 1, 0, 0, 0) -- "%MONEY%. Sssilence now."
     ,(11, 611, 4, 0, 1, 0, 0, 0) -- "More casssh down the drain... Take %MONEY%."

      -- Creeper / Accept
     ,(12, 612, 1, 0, 0, 0, 0, 1) -- That''sss sssorted then. Sssee you!
     ,(13, 613, 2, 0, 0, 0, 0, 1) -- Sssoo here you go... Bye.
     ,(14, 614, 3, 0, 0, 0, 0, 1) -- That''sss cheap to get rid of you.
     ,(15, 615, 4, 0, 0, 0, 0, 1) -- Back to sssquare one I go...

      -- Creeper / Reject
     ,(16, 616, 1, 0, 0, 0, 1, 0) -- Let''sss see how much your life isss worth.
     ,(17, 617, 2, 0, 0, 0, 1, 0) -- Sssorry, I can''t let you have any more.
     ,(18, 618, 3, 0, 0, 0, 1, 0) -- I tire of your greed. Let''sss finish this.
     ,(19, 619, 4, 0, 0, 0, 1, 0) -- Thisss is all I''ve got in life. Maybe I''ll sssell you.


      -- Skeleton
     ,(20, 620, 1, 1, 0, 0, 0, 0) -- "I goT soMe %MONEY% iN my PigGy skuLl."
     ,(21, 621, 2, 1, 0, 0, 0, 0) -- "HeRe's %MONEY%."
     ,(22, 622, 3, 1, 0, 0, 0, 0) -- "%MONEY%, alL yoU're getTin'."
     ,(23, 623, 4, 1, 0, 0, 0, 0) -- "%MONEY%, aNy moRe aNd i'M scrEweD."

     ,(24, 624, 1, 0, 1, 0, 0, 0) -- "BiT of a GolD digGer, mEaTsAcK? HeRe's %MONEY%."
     ,(25, 625, 2, 0, 1, 0, 0, 0) -- "MoRe? HoW's %MONEY%?"
     ,(26, 626, 3, 0, 1, 0, 0, 0) -- "%MONEY%, alL yoU're getTin'."
     ,(27, 627, 4, 0, 1, 0, 0, 0) -- "%MONEY%, aNy moRe aNd i'M scrEweD."

     ,(28, 628, 1, 0, 1, 0, 0, 0) -- "YoU'rE shAkiN mE looSer tHan I Am! HeRe's %MONEY%."
     ,(29, 629, 2, 0, 1, 0, 0, 0) -- "%MONEY% enOugH?"
     ,(30, 630, 3, 0, 1, 0, 0, 0) -- "DaRn sQuisHy soD. %MONEY%. Go."
     ,(31, 631, 4, 0, 1, 0, 0, 0) -- "%MONEY%, jUst lEaVe mE alOnE."

     -- Skeleton / Accept
     ,(32, 632, 1, 0, 0, 0, 0, 1) -- I wAs gOiNg to uSe tHaT tO pAy aWaY tHosE dOgs thAt kEep chAsin'' mE! Oh wEll.
     ,(33, 633, 2, 0, 0, 0, 0, 1) -- I''Ll gO nOw.
     ,(34, 634, 3, 0, 0, 0, 0, 1) -- ThAt''lL eNd oUr proCeediN''s theN.
     ,(35, 635, 4, 0, 0, 0, 0, 1) -- WoRks fOr mE...

     -- Skeleton / Reject
     ,(36, 636, 1, 0, 0, 0, 1, 0) -- SoRrY, nOt hApPenin''.
     ,(37, 637, 2, 0, 0, 0, 1, 0) -- No...
     ,(38, 638, 3, 0, 0, 0, 1, 0) -- DoN''t yoU evEn tRy!
     ,(39, 639, 4, 0, 0, 0, 1, 0) -- wHy bOtheR wiTh thIs anYmorE?


      -- Spider
     ,(40, 640, 1, 1, 0, 0, 0, 0) -- "The %NAME% unveils %MONEY%, though it's not clear where from..."
     ,(41, 641, 2, 1, 0, 0, 0, 0) -- "The %NAME% cautiously drops down %MONEY%."
     ,(42, 642, 3, 1, 0, 0, 0, 0) -- "The %NAME% spits %MONEY% at you."
     ,(43, 643, 4, 1, 0, 0, 0, 0) -- "The %NAME% plucks %MONEY% from its hairs."

     ,(44, 644, 1, 0, 1, 0, 0, 0) -- "A total of %MONEY% seems to have been retrieved from the some origin..."
     ,(45, 645, 2, 0, 1, 0, 0, 0) -- "The %NAME% slowly adds to the pile, for a total of %MONEY%."
     ,(46, 646, 3, 0, 1, 0, 0, 0) -- "More is spat at you, totalling %MONEY% overall."
     ,(47, 647, 4, 0, 1, 0, 0, 0) -- "From a deeper dive into its filth, the %NAME% makes for a total of %MONEY%."

     ,(48, 648, 1, 0, 1, 0, 0, 0) -- "As the mystery of the origin deepens, so does the value as the %NAME% presents a total of %MONEY%."
     ,(49, 649, 2, 0, 1, 0, 0, 0) -- "With great hesitation, the pile is expanded further for a total of %MONEY%."
     ,(50, 650, 3, 0, 1, 0, 0, 0) -- "The %NAME% seems to now be depositing from the less favourable end for a total of %MONEY%."
     ,(51, 651, 4, 0, 1, 0, 0, 0) -- "You gag as the %NAME% retrieves from within itself once again, totalling %MONEY%."

     -- Spider / Accept
     ,(52, 452, 1, 0, 0, 0, 0, 1) -- The spider takes its leave...
     ,(53, 452, 2, 0, 0, 0, 0, 1) -- The spider takes its leave...
     ,(54, 452, 3, 0, 0, 0, 0, 1) -- The spider takes its leave...
     ,(55, 452, 4, 0, 0, 0, 0, 1) -- The spider takes its leave...

     -- Spider / Reject
     ,(56, 453, 1, 0, 0, 0, 1, 0) -- The spider has become antagonistic!
     ,(57, 453, 2, 0, 0, 0, 1, 0) -- The spider has become antagonistic!
     ,(58, 453, 3, 0, 0, 0, 1, 0) -- The spider has become antagonistic!
     ,(59, 453, 4, 0, 0, 0, 1, 0) -- The spider has become antagonistic!


      -- Zombie
     ,(60, 660, 1, 1, 0, 0, 0, 0) -- "Hargh... Only have pocket lint... and %MONEY%."
     ,(61, 661, 2, 1, 0, 0, 0, 0) -- "Only... %MONEY%."
     ,(62, 662, 3, 1, 0, 0, 0, 0) -- "%MONEY%... Take."
     ,(63, 663, 4, 1, 0, 0, 0, 0) -- "%MONEY%... and no kill... please."

     ,(64, 664, 1, 0, 1, 0, 0, 0) -- "More... not much deeper pocket... have %MONEY%."
     ,(65, 665, 2, 0, 1, 0, 0, 0) -- "Savings... Urgh... have %MONEY%."
     ,(66, 666, 3, 0, 1, 0, 0, 0) -- "Murgh... More... %MONEY%. Go."
     ,(67, 667, 4, 0, 1, 0, 0, 0) -- "%MONEY%. Take... Urg... all."

     ,(68, 668, 1, 0, 1, 0, 0, 0) -- "I check... Other... Pocket... Got %MONEY%."
     ,(69, 669, 2, 0, 1, 0, 0, 0) -- "Bur... %MONEY%... is more..."
     ,(70, 670, 3, 0, 1, 0, 0, 0) -- "%MONEY%... Rurgh... Really take and go... now."
     ,(71, 671, 4, 0, 1, 0, 0, 0) -- "Rurgh... Running... out... Have %MONEY%."

      -- Zombie / Accept
     ,(72, 672, 1, 0, 0, 0, 0, 1) -- Harr... Don''t spend... all at once.
     ,(73, 673, 2, 0, 0, 0, 0, 1) -- Thank... for spare... life... Gurhbye.
     ,(74, 674, 3, 0, 0, 0, 0, 1) -- Hope... happy.
     ,(75, 675, 4, 0, 0, 0, 0, 1) -- Buuu... Now have... nothing...

      -- Zombie / Reject
     ,(76, 676, 1, 0, 0, 0, 1, 0) -- Hargh! Sorry... mine.
     ,(77, 677, 2, 0, 0, 0, 1, 0) -- Gurgh! Can''t... give.
     ,(78, 678, 3, 0, 0, 0, 1, 0) -- Grah! S...top.
     ,(79, 679, 4, 0, 0, 0, 1, 0) -- Buh... Not... worth.
;