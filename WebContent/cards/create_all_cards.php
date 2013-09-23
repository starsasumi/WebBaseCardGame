<!--
 * Copyright 2013 Xiaxing Shi.
 *
 * This file is part of WebBaseCardGame.
 *
 * WebBaseCardGame is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * WebBaseCardGame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with WebBaseCardGame.
 * 
 * If not, see <http://www.gnu.org/licenses/>.
-->
<head>
<link rel="stylesheet" type="text/css" href="../css/cards.css" />
</head>
<body>
<?php
$colors = array('red', 'yellow', 'green', 'blue', 'wild');
$marks = array(0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,'draw_two','draw_two','skip','skip','reverse','reverse');
$id=-1;
foreach ($colors as $color) {
	if ($color != 'wild') {
		foreach ($marks as $mark) {
/* 			echo $mark.'<br />'; */
			$id++;
			$card_logo;
			if (is_string($mark)) {
				if ($mark == 'draw_two') $card_logo = '+2';
				if ($mark == 'skip') $card_logo = 'S';
				if ($mark == 'reverse') $card_logo = 'R';
			} elseif (is_integer($mark)) {
				$card_logo = $mark;
			}
			print_card($id,$color, $mark, $card_logo);
		}
	} else {
		for ($i = 0; $i < 8 ; $i++) {
			$id++;
			$card_logo;
			$mark;
			if ($i < 4) {
				$mark = 'wild';
				$card_logo = 'W';
			} else {
				$mark = 'wild_draw_four';
				$card_logo = '+4';
			}
			print_card($id,$color, $mark, $card_logo);
		}
	}
}

function print_card($id,$color, $mark, $card_logo) { ?>
<div class='card <?php echo ($mark === 'wild')?'':($color.' '); ?><?php echo (is_string($mark))?($mark):('num'.$mark); ?>' id='card_<?php echo $id; ?>' data-cardId='<?php echo $id; ?>' data-cardColor='<?php echo $color; ?>' data-cardMark='<?php echo $mark; ?>'>
	<div class='card_logo'><?php echo $card_logo; ?></div>
	<div class='card_pattern'><?php echo $card_logo; ?></div>
</div>
<?php
}
?>
</body>