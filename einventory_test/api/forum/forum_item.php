<?php

$item = [
	[
		"user_name" => 100,
		"avatar" => "http://192.168.43.249/einventory_test/avatar.png",
		"response" => 'This is response',
		"created_at" => '2019-08-09 09:00:00'
	],
	[
		"user_name" => 100,
		"avatar" => "http://192.168.43.249/einventory_test/avatar.png",
		"response" => 'This is response',
		"created_at" => '2019-08-09 10:00:00'
	],
	[
		"user_name" => 100,
		"avatar" => "http://192.168.43.249/einventory_test/avatar.png",
		"response" => 'This is response',
		"created_at" => '2019-08-09 11:00:00'
	]
];

echo json_encode($item);
