INSERT INTO ScriptLine (ScriptLineId, ScriptLineTypeId, ScriptLineToneId, TextContentId)
VALUES
      -- Creeper
      (600, 0, 0, 600) -- "Here'sss an explosssive offer for you then, how'sss %MONEY%?"
     ,(601, 0, 0, 601) -- "Alright... Take thisss %MONEY%."
     ,(602, 0, 0, 602) -- "I have to pay you to go away? Fine. Here'sss %MONEY%."
     ,(603, 0, 0, 603) -- "My life'sss only worth %MONEY%..."

     ,(604, 0, 0, 604) -- "Ssso I'm not going away that easy? Alright, here'sss %MONEY%."
     ,(605, 0, 0, 605) -- "You desssire more? Here's %MONEY%."
     ,(606, 0, 0, 606) -- "...Oh? Isss that ssstill not enough? You're insufferable. Take thisss %MONEY%."
     ,(607, 0, 0, 607) -- "No richesss for Creepersss... Here'sss %MONEY%."

     ,(608, 0, 0, 608) -- "Alright, friendsss and family ssspecial: %MONEY%."
     ,(609, 0, 0, 609) -- "I'll be in sssoo much trouble giving you %MONEY%..."
     ,(610, 0, 0, 610) -- "%MONEY%. Sssilence now."
     ,(611, 0, 0, 611) -- "More casssh down the drain... Take %MONEY%."

      -- Creeper / Accept
     ,(612, 0, 2, 612) -- That''sss sssorted then. Sssee you!
     ,(613, 0, 2, 613) -- Sssoo here you go... Bye.
     ,(614, 0, 2, 614) -- That''sss cheap to get rid of you.
     ,(615, 0, 2, 615) -- Back to sssquare one I go...

      -- Creeper / Reject
     ,(616, 0, 1, 616) -- Let''sss see how much your life isss worth.
     ,(617, 0, 1, 617) -- Sssorry, I can''t let you have any more.
     ,(618, 0, 1, 618) -- I tire of your greed. Let''sss finish this.
     ,(619, 0, 1, 619) -- Thisss is all I''ve got in life. Maybe I''ll sssell you.

      -- Skeleton
     ,(620, 0, 0, 620) -- "I goT soMe %MONEY% iN my PigGy skuLl."
     ,(621, 0, 0, 621) -- "HeRe's %MONEY%."
     ,(622, 0, 0, 622) -- "%MONEY%, alL yoU're getTin'."
     ,(623, 0, 0, 623) -- "%MONEY%, aNy moRe aNd i'M scrEweD."

     ,(624, 0, 0, 624) -- "BiT of a GolD digGer, mEaTsAcK? HeRe's %MONEY%."
     ,(625, 0, 0, 625) -- "MoRe? HoW's %MONEY%?"
     ,(626, 0, 0, 626) -- "HeRe's %MONEY% to gEt oUt of mY eyE socKet hoLes."
     ,(627, 0, 0, 627) -- "%MONEY%, tAke iT or taKe mE. At thIs PoInt, I dON't cAre."

     ,(628, 0, 0, 628) -- "YoU'rE shAkiN mE looSer tHan I Am! HeRe's %MONEY%."
     ,(629, 0, 0, 629) -- "%MONEY% enOugH?"
     ,(630, 0, 0, 630) -- "DaRn sQuisHy soD. %MONEY%. Go."
     ,(631, 0, 0, 631) -- "%MONEY%, jUst lEaVe mE alOnE."

     -- Skeleton / Accept
     ,(632, 0, 2, 632) -- I wAs gOiNg to uSe tHaT tO pAy aWaY tHosE dOgs thAt kEep chAsin'' mE! Oh wEll.
     ,(633, 0, 2, 633) -- I''Ll gO nOw.
     ,(634, 0, 2, 634) -- ThAt''lL eNd oUr proCeediN''s theN.
     ,(635, 0, 2, 635) -- WoRks fOr mE...

     -- Skeleton / Reject
     ,(636, 0, 1, 636) -- SoRrY, nOt hApPenin''.
     ,(637, 0, 1, 637) -- No...
     ,(638, 0, 1, 638) -- DoN''t yoU evEn tRy!
     ,(639, 0, 1, 639) -- wHy bOtheR wiTh thIs anYmorE?

      -- Spider
     ,(640, 1, 0, 640) -- "The %NAME% unveils %MONEY%, though it's not clear where from..."
     ,(641, 1, 0, 641) -- "The %NAME% cautiously drops down %MONEY%."
     ,(642, 1, 0, 642) -- "The %NAME% spits %MONEY% at you."
     ,(643, 1, 0, 643) -- "The %NAME% plucks %MONEY% from its hairs."

     ,(644, 1, 0, 644) -- "A total of %MONEY% seems to have been retrieved from the some origin..."
     ,(645, 1, 0, 645) -- "The %NAME% slowly adds to the pile, for a total of %MONEY%."
     ,(646, 1, 0, 646) -- "More is spat at you, totalling %MONEY% overall."
     ,(647, 1, 0, 647) -- "From a deeper dive into its filth, the %NAME% makes for a total of %MONEY%."

     ,(648, 1, 0, 648) -- "As the mystery of the origin deepens, so does the value as the %NAME% presents a total of %MONEY%."
     ,(649, 1, 0, 649) -- "With great hesitation, the pile is expanded further for a total of %MONEY%."
     ,(650, 1, 0, 650) -- "The %NAME% seems to now be depositing from the less favourable end for a total of %MONEY%."
     ,(651, 1, 0, 651) -- "You gag as the %NAME% retrieves from within itself once again, totalling %MONEY%."

     -- Spider / Accept
     --Reuses ID 452 from ItemNegotiation -- The spider takes its leave...

     -- Spider / Reject
     -- Reuses ID 453 from ItemNegotiation -- The spider has become antagonistic!

      -- Zombie
     ,(660, 0, 0, 660) -- "Hargh... Only have pocket lint... and %MONEY%."
     ,(661, 0, 0, 661) -- "Only... %MONEY%."
     ,(662, 0, 0, 662) -- "%MONEY%... Take."
     ,(663, 0, 0, 663) -- "%MONEY%... and no kill... please."

     ,(664, 0, 0, 664) -- "More... not much deeper pocket... have %MONEY%."
     ,(665, 0, 0, 665) -- "Savings... Urgh... have %MONEY%."
     ,(666, 0, 0, 666) -- "Murgh... More... %MONEY%. Go."
     ,(667, 0, 0, 667) -- "%MONEY%. Take... Urg... all."

     ,(668, 0, 0, 668) -- "I check... Other... Pocket... Got %MONEY%."
     ,(669, 0, 0, 669) -- "Bur... %MONEY%... is more..."
     ,(670, 0, 0, 670) -- "%MONEY%... Rurgh... Really take and go... now."
     ,(671, 0, 0, 671) -- "Rurgh... Running... out... Have %MONEY%."
     
      -- Zombie / Accept
     ,(672, 0, 2, 672) -- Harr... Don''t spend... all at once.
     ,(673, 0, 2, 673) -- Thank... for spare... life... Gurhbye.
     ,(674, 0, 2, 674) -- Hope... happy.
     ,(675, 0, 2, 675) -- Buuu... Now have... nothing...

      -- Zombie / Reject
     ,(676, 0, 1, 676) -- Hargh! Sorry... mine.
     ,(677, 0, 1, 677) -- Gurgh! Can''t... give.
     ,(678, 0, 1, 678) -- Grah! S...top.
     ,(679, 0, 1, 679) -- Buh... Not... worth.
;