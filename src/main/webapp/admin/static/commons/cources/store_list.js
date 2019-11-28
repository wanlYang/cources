layui.use(['form', 'layer', 'table', 'laytpl','laydate','jquery'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		laytpl = layui.laytpl,
		table = layui.table,
		laydate = layui.laydate;
	// 导师列表
	var tableIns = table.render({
		elem: '#storeList',
		url: getRealPath() + '/admin/cources/table/storefront/list/table',
		cellMinWidth: 95,
		method: "POST",
		height: "full-125",
		id: "storeListTable",
		where : {'admin_id' : currentAdminId_Index},
		cols: [
				[	
				{
					sort: true,
					field: "id",
					title: "ID",
					align: "center",
				},
				{
					field: 'name',
					title: '门店名称',
                    style: 'cursor: pointer;',
					align: 'center',

				},
				{
					title: '操作',
					templet: '#storeListBar',
					fixed: "right",
					align: "center"
				}
			]
		],
		text: {
			none: '暂无相关数据' // 默认：无数据。注：该属性为 layui 2.2.5 开始新增
		},
		response: {
			statusName: 'status', // 规定数据状态的字段名称，默认：code
			statusCode: 200, // 规定成功的状态码，默认：0
			msgName: 'message', // 规定状态信息的字段名称，默认：msg
			countName: 'count', // 规定数据总数的字段名称，默认：count
			dataName: 'data' // 规定数据列表的字段名称，默认：data
		},
		toolbar: true
	});
	// 添加
	function addStore() {
		var index = layui.layer.open({
			title: "添加门店",
			type: 2,
			content: getRealPath() + "/admin/cources/table/store/add",
			success: function(layero, index) {
				setTimeout(function() {
					layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			},
			end: function() {
				$(window).unbind("resize");
			}
		})
		layui.layer.full(index);
		window.sessionStorage.setItem("index", index);
		// 改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
		$(window).on("resize", function() {
			layui.layer.full(window.sessionStorage.getItem("index"));
		})
	}
	$(".addNews_btn").click(function() {
		addStore();
	})
	// 列表操作
	table.on('tool(storeList)', function(obj) {
		var layEvent = obj.event,
			data = obj.data;
		// 监听操作
		if(layEvent === 'edit') { // 删除
			var index = layui.layer.open({
				title: "编辑门店",
				type: 2,
				content: getRealPath() + "/admin/cources/table/store/edit",
				success: function(layero, index) {
					var body = layui.layer.getChildFrame('body', index);
					var iframeWindow = window[layero.find('iframe')[0]['name']];
					body.find(".store_id").val(data.id);
					body.find(".name").val(data.name);
					setTimeout(function() {
						layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
							tips: 3
						});
					}, 500)
				},
				end: function() {
					$(window).unbind("resize");
				}
			})
			layui.layer.full(index);
			window.sessionStorage.setItem("index", index);
			// 改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
			$(window).on("resize", function() {
				layui.layer.full(window.sessionStorage.getItem("index"));
			})
		}
	});
})