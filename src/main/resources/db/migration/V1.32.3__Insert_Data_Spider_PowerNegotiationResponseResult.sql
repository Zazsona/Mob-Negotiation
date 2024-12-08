INSERT INTO PowerNegotiationResponseResult (PowerNegotiationResponseId, PersonalityId, SuccessReplyScriptLineId, SuccessNextPowerNegotiationPromptId, FailureReplyScriptLineId, FailureNextPowerNegotiationPromptId, SuccessRate)
VALUES
    -- The spider doesn't seem to understand...

       -- Say it again, but louder.
       (60, 1, 1402, 20, 1406, null, 0.75)
      ,(60, 2, 1403, 20, 1407, null, 0.20)
      ,(60, 3, 1404, 20, 1408, null, 0.25)
      ,(60, 4, 1405, 20, 1409, null, 0.20)

       -- Say it again, but slower.
       (61, 1, 1411, 20, 1415, null, 0.05)
      ,(61, 2, 1412, 20, 1416, null, 0.95)
      ,(61, 3, 1413, 20, 1417, null, 0.00)
      ,(61, 4, 1414, 20, 1418, null, 0.60)

       -- Wait.
       (62, 1, 1420, 20, 1424, null, 0.05)
      ,(62, 2, 1421, 20, 1425, null, 0.60)
      ,(62, 3, 1422, 20, 1426, null, 0.05)
      ,(62, 4, 1423, 20, 1427, null, 0.95)

    -- It seems to be eyeing up your inventory...

       -- Eye it back.
       (63, 1, 1430, null, 1434, null, 0.15)
      ,(63, 2, 1431, null, 1435, null, 0.05)
      ,(63, 3, 1432, null, 1436, null, 0.20)
      ,(63, 4, 1433, null, 1437, null, 0.40)

       -- Pretend to offer something.
       (64, 1, 1439, null, 1443, null, 0.85)
      ,(64, 2, 1440, null, 1444, null, 0.10)
      ,(64, 3, 1441, null, 1445, null, 0.00)
      ,(64, 4, 1442, null, 1446, null, 0.10)

       -- Shake your head.
       (65, 1, 1448, null, 1452, null, 0.15)
      ,(65, 2, 1449, null, 1453, null, 0.85)
      ,(65, 3, 1450, null, 1454, null, 0.60)
      ,(65, 4, 1451, null, 1455, null, 0.40)

    -- It appears to be deep in thought...

       -- Say it again.
       (66, 1, 1458, null, 1462, null, 0.10)
      ,(66, 2, 1459, null, 1463, null, 0.35)
      ,(66, 3, 1460, null, 1464, null, 0.00)
      ,(66, 4, 1461, null, 1465, null, 0.15)

       -- Pull a pose like The Thinker.
       (67, 1, 1467, null, 1471, null, 0.95)
      ,(67, 2, 1468, null, 1472, null, 0.20)
      ,(67, 3, 1469, null, 1473, null, 0.50)
      ,(67, 4, 1470, null, 1474, null, 0.85)

       -- Whistle a tune while waiting.
       (68, 1, 1476, null, 1480, null, 0.75)
      ,(68, 2, 1477, null, 1481, null, 0.75)
      ,(68, 3, 1478, null, 1482, null, 0.05)
      ,(68, 4, 1479, null, 1483, null, 0.50)

    -- The spider seems disinterested...

       -- Snap your fingers at it.
       (69, 1, 1486, 24, 1490, null, 0.05)
      ,(69, 2, 1487, 24, 1491, null, 0.20)
      ,(69, 3, 1488, 24, 1492, null, 0.70)
      ,(69, 4, 1489, 24, 1493, null, 0.05)

       -- Tell it a fairy-tale.
       (70, 1, 1495, 24, 1499, null, 0.40)
      ,(70, 2, 1496, 24, 1500, null, 0.90)
      ,(70, 3, 1497, 24, 1501, null, 0.05)
      ,(70, 4, 1498, 24, 1502, null, 0.90)

       -- Stare into its deep beautiful eyes...
       (71, 1, 1504, 24, 1508, null, 0.05)
      ,(71, 2, 1505, 24, 1509, null, 0.30)
      ,(71, 3, 1506, 24, 1510, null, 0.85)
      ,(71, 4, 1507, 24, 1511, null, 0.05)

    -- The spider is beckoning you closer...

       -- Go closer.
       (72, 1, 1514, null, 1518, null, 0.90)
      ,(72, 2, 1515, null, 1519, null, 0.50)
      ,(72, 3, 1516, null, 1520, null, 0.30)
      ,(72, 4, 1517, null, 1521, null, 0.05)

       -- Stay back.
       (73, 1, 1523, null, 1527, null, 0.50)
      ,(73, 2, 1524, null, 1528, null, 0.10)
      ,(73, 3, 1525, null, 1529, null, 0.20)
      ,(73, 4, 1526, null, 1530, null, 0.80)

       -- Gesture back.
       (74, 1, 1532, null, 1536, null, 0.85)
      ,(74, 2, 1533, null, 1537, null, 0.25)
      ,(74, 3, 1534, null, 1538, null, 0.025)
      ,(74, 4, 1535, null, 1539, null, 0.50)


    -- It's looking at you expectantly...

       -- Show off your loot.
       (75, 1, 1542, null, 1546, null, 0.30)
      ,(75, 2, 1543, null, 1547, null, 0.20)
      ,(75, 3, 1544, null, 1548, null, 0.85)
      ,(75, 4, 1545, null, 1549, null, 0.05)

       -- Bust a move.
       (76, 1, 1551, null, 1555, null, 0.95)
      ,(76, 2, 1552, null, 1556, null, 0.30)
      ,(76, 3, 1553, null, 1557, null, 0.025)
      ,(76, 4, 1554, null, 1558, null, 0.15)

       -- Enlighten it with knowledge.
       (77, 1, 1560, null, 1564, null, 0.10)
      ,(77, 2, 1561, null, 1565, null, 0.30)
      ,(77, 3, 1562, null, 1566, null, 0.60)
      ,(77, 4, 1563, null, 1567, null, 0.85)


    -- The spider is trying to get away...

       -- Stop it.
       (78, 1, 1570, 27, 1574, null, 0.40)
      ,(78, 2, 1571, 27, 1575, null, 0.40)
      ,(78, 3, 1572, 27, 1576, null, 0.75)
      ,(78, 4, 1573, 27, 1577, null, 0.60)

       -- Let it leave.
       (79, 1, 1579, 27, 1583, null, 0.05)
      ,(79, 2, 1580, 27, 1584, null, 0.85)
      ,(79, 3, 1581, 27, 1585, null, 0.00)
      ,(79, 4, 1582, 27, 1586, null, 0.75)

       -- Plead for it to stay
       (80, 1, 1588, 27, 1582, null, 0.15)
      ,(80, 2, 1589, 27, 1593, null, 0.60)
      ,(80, 3, 1590, 27, 1594, null, 0.85)
      ,(80, 4, 1591, 27, 1595, null, 0.40)

    -- It looks like it might want to join you on your adventures...

       -- Gently let it down.
       (81, 1, 1598, null, 1602, null, 0.30)
      ,(81, 2, 1599, null, 1603, null, 0.85)
      ,(81, 3, 1600, null, 1604, null, 0.30)
      ,(81, 4, 1601, null, 1605, null, 0.15)

       -- Reject it.
       (82, 1, 1607, null, 1611, null, 0.10)
      ,(82, 2, 1608, null, 1612, null, 0.00)
      ,(82, 3, 1609, null, 1613, null, 0.90)
      ,(82, 4, 1610, null, 1614, null, 0.25)

       -- Explain that you work alone.
       (83, 1, 1616, null, 1620, null, 0.05)
      ,(83, 2, 1617, null, 1621, null, 0.15)
      ,(83, 3, 1618, null, 1622, null, 0.40)
      ,(83, 4, 1619, null, 1623, null, 0.85)

    -- It seems curious about who you are...

       -- Tell it that's secret.
       (84, 1, 1626, null, 1630, null, 0.10)
      ,(84, 2, 1627, null, 1631, null, 0.05)
      ,(84, 3, 1628, null, 1632, null, 0.30)
      ,(84, 4, 1629, null, 1633, null, 0.90)

       -- Tell it you're a fancy banker.
       (85, 1, 1635, null, 1639, null, 0.90)
      ,(85, 2, 1636, null, 1640, null, 0.75)
      ,(85, 3, 1637, null, 1641, null, 0.02)
      ,(85, 4, 1638, null, 1642, null, 0.08)

       -- Tell it your life story.
       (86, 1, 1644, null, 1648, null, 0.05)
      ,(86, 2, 1645, null, 1649, null, 0.95)
      ,(86, 3, 1646, null, 1650, null, 0.25)
      ,(86, 4, 1647, null, 1651, null, 0.05)
;