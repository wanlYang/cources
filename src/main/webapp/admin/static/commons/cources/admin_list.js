layui.use(['form', 'layer', 'table', 'laytpl','laydate','jquery'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		laytpl = layui.laytpl,
		table = layui.table,
		laydate = layui.laydate;
	// 导师列表
	var tableIns = table.render({
		elem: '#adminList',
		url: getRealPath() + '/admin/cources/table/admin/list/table',
		cellMinWidth: 95,
		method: "POST",
		height: "full-125",
		id: "adminListTable",
		cols: [
				[	
				{
					sort: true,
					field: "id",
					title: "ID",
					align: "center",
				},
				{
					field: 'adminname',
					title: '管理名称',
                    style: 'cursor: pointer;',
					align: 'center',

				},
				{
					field: 'storeFront',
					title: '管理门店',
					style: 'cursor: pointer;',
					align: 'center',
					templet: function(d) {
						var store = "未绑定";
						if (currentAdminId_Index != d.id){
							$.ajax({
								type: "POST",
								async: false,
								url: getRealPath() + "/admin/cources/table/admin/list/get/front",
								data: {"admin_id":d.id},
								success: function(result) {
									if (result.data == null){

									}else{
										store = result.data.name;
									}
								}
							});
						}else{
							return "超级管理";
						}
						return store;
					}
				},
				{
					title: '操作',
					templet: '#adminListBar',
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
	function addAdmin() {
		var index = layui.layer.open({
			title: "添加管理",
			type: 2,
			content: getRealPath() + "/admin/cources/table/admin/add",
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
		addAdmin();
	})
	// 列表操作
	table.on('tool(adminList)', function(obj) {
		var layEvent = obj.event,
			data = obj.data;
		// 监听操作
		if(layEvent === 'del') { // 删除
			layer.confirm('确定删除此管理?', {
				icon: 3,
				title: '提示信息'
			}, function (index) {
				$.ajax({
					type: "POST",
					async: false,
					url: getRealPath() + "/admin/cources/table/admin/del",
					data: {"admin_id":data.id},
					success: function(result) {
						if (result.status == 200){
							layer.msg("删除成功!")
							obj.del();
						}
					}
				});
			});

		}else if(layEvent === 'edit'){
			const index = layui.layer.open({
				title: "编辑管理",
				type: 2,
				area: ['500px', '550px'],
				content: getRealPath() + "/admin/cources/table/admin/edit",
				success: function (layero, index) {
					const body = layui.layer.getChildFrame('body', index);
					const iframeWindow = window[layero.find('iframe')[0]['name']];
					body.find(".adminname").val(data.adminname);
					body.find(".id").val(data.id);
					//获取店面信息
					var store_ = "";
					$.ajax({
						type: "POST",
						async: false,
						url: getRealPath() + "/admin/cources/table/admin/list/get/front",
						data: {"admin_id":data.id},
						success: function(result) {
							if (result.data == null){
							}else{
								store_ = result.data.id;
							}
						}
					});
					$.ajax({
						type: "POST",
						async: false,
						url: getRealPath() + "/admin/cources/table/week/list/front",
						data: {"admin_id":currentAdminId_Index},
						success: function(result) {
							if(result.status == 200) {
								var html = "";
								$.each(result.data,function (index,item) {
									html += '<option value="'+item.id+'">'+item.name+'</option>';

								})
								body.find("#store").append(html);
								if (typeof (iframeWindow.layui.form) != "undefined") {
									iframeWindow.layui.form.render();
								}
								layer.msg("获取数据成功!")
							} else {
								top.layer.close(index);
								top.layer.msg("获取失败！" + result.message);
							}
						}
					});
					body.find("#store").each(function() {
						// this代表的是<option></option>，对option再进行遍历
						$(this).children("option").each(function() {
							// 判断需要对那个选项进行回显
							if (this.value == store_) {
								// 进行回显
								$(this).attr("selected","selected");
							}
						});
					})
					if (typeof (iframeWindow.layui.form) != "undefined") {
						iframeWindow.layui.form.render();
					}
				},
				end: function () {

				}
			});
		}
	});
})