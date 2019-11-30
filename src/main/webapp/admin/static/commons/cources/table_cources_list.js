layui.use(['form', 'layer', 'jquery'], function () {
    const form = layui.form,
        $ = layui.jquery,
        layer = parent.layer === undefined ? layui.layer : top.layer;

    function strNubAry(str) {
        var ary = [];
        fnFas(str);

        function fnFas(str) {
            var s1 = str.charAt(0);
            var _str = '';
            var n = 0;

            for (var i = 0; i < str.length; i++) {
                if (str.charAt(i) == s1)
                    n++;
                else
                    _str += str.charAt(i);
            }
            ary.push(n)
            if (_str.length > 0) {
                fnFas(_str);
            }
        }

        return ary;
    };
    function check() {
        var userAgentInfo=navigator.userAgent;
        var Agents =new Array("Android","iPhone","SymbianOS","Windows Phone","iPad","iPod");
        var flag=true;
        for(var v=0;v<Agents.length;v++) {
            if(userAgentInfo.indexOf(Agents[v])>0) {
                flag=false;
                break;
            }
        }
        return flag;
    }
    const vm = new Vue({
        el: '#app',
        data: {
            current: 0,
            timer: null,
            show:true,
            message :"温馨提示：鼠标单击删除课程,双击修改课程;未设置的课程为橙色",
            fronts: [],
            front_id: "",
            front_name: "",
            week: [],
            week_id_list: ["11976352101574247112256", "21964327101574247282899", "39657101421574247168608", "47196108431574247199561", "51065329141574247214318", "67341681051574247226155", "71046358971574247275310"],
            monday: [],
            tuesday: [],
            wednesday: [],
            thursday: [],
            friday: [],
            saturday: [],
            superAdminId:currentAdminId_Index,
            sunday: [],
            tempWeek: ["monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"]
        },
        created: function () {
            //为了在内部函数能使用外部函数的this对象，要给它赋值了一个名叫self的变量。
            const self = this;
            //店面ID

            //实际使用时的提交信息
            //获取星期数据
            axios.post(getRealPath() + "/admin/cources/table/week/list")
                .then(function (response) {
                    self.week = response.data.data;
                    layer.msg("获取数据成功!")
                })
                .catch(function (error) { // 请求失败处理
                    layer.msg("获取数据失败!")
                    console.log(error);
                });
            //获取店面信息
            $.ajax({
                type: "POST",
                async: false,
                url: getRealPath() + "/admin/cources/table/week/list/front",
                data: {"admin_id":currentAdminId_Index},
                success: function(result) {
                    if(result.status == 200) {
                        self.fronts = result.data;
                        self.front_id = self.fronts[0].id;
                        layer.msg("获取数据成功!")
                    } else {
                        top.layer.close(index);
                        top.layer.msg("添加失败！" + result.message);
                    }
                }
            });
            self.monday = this.getWeekCources(self.front_id, self.week_id_list[0], self.tempWeek[0]);
            self.tuesday =  this.getWeekCources(self.front_id, self.week_id_list[1], self.tempWeek[1])
            self.wednesday =this.getWeekCources(self.front_id, self.week_id_list[2], self.tempWeek[2])
            self.thursday = this.getWeekCources(self.front_id, self.week_id_list[3], self.tempWeek[3])
            self.friday = this.getWeekCources(self.front_id, self.week_id_list[4], self.tempWeek[4])
            self.saturday = this.getWeekCources(self.front_id, self.week_id_list[5], self.tempWeek[5])
            self.sunday = this.getWeekCources(self.front_id, self.week_id_list[6], self.tempWeek[6])

        },
        methods: {
            getWeekCources: function (front, week_id, describe) {
                var data_ = null;
                $.ajax({
                    type: "POST",
                    async: false,
                    url: getRealPath() + "/admin/cources/table/list/" + describe,
                    data: {"front":front,'week':week_id},
                    success: function(result) {
                        data_ =  result.data;
                    }
                });
                return data_;
            },
            storeTransformation: async function (front, index, event) {
                this.current = index;
                const el = event.currentTarget;
                vm.front_id = front.id;
                vm.front_name = front.name;
                vm.monday = this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[0], this.tempWeek[0]);
                vm.tuesday = this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[1], this.tempWeek[1]);
                vm.wednesday = this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[2], this.tempWeek[2]);
                vm.thursday = this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[3], this.tempWeek[3]);
                vm.friday = this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[4], this.tempWeek[4]);
                vm.saturday = this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[5], this.tempWeek[5]);
                vm.sunday = this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[6], this.tempWeek[6]);

            },
            delCources: function (cources, index) {
                const _this = this;
                let timers = this.timer;
                if (timers) {
                    window.clearTimeout(timers);
                    this.timer = null;
                }
                this.timer = window.setTimeout(function () {
                    layer.confirm('确定删除此课程表?', {
                        icon: 3,
                        title: '提示信息'
                    }, function (index) {
                        const params_del = new URLSearchParams();
                        params_del.append('id', cources.id);
                        axios.post(getRealPath() + "/admin/cources/table/delete/submit", params_del)
                            .then(function (response) {
                                layer.msg("删除成功!");
                                if (cources.week.english === _this.tempWeek[0]) {
                                    _this.monday.some(function (item, index) {
                                        if (item.id == cources.id) {
                                            _this.monday.splice(index, 1);
                                        }
                                    })
                                }
                                if (cources.week.english === _this.tempWeek[1]) {
                                    _this.tuesday.some(function (item, index) {
                                        if (item.id == cources.id) {
                                            _this.tuesday.splice(index, 1);
                                        }
                                    })
                                }
                                if (cources.week.english === _this.tempWeek[2]) {
                                    _this.wednesday.some(function (item, index) {
                                        if (item.id == cources.id) {
                                            _this.wednesday.splice(index, 1);
                                        }
                                    })
                                }
                                if (cources.week.english === _this.tempWeek[3]) {
                                    _this.thursday.some(function (item, index) {
                                        if (item.id == cources.id) {
                                            _this.thursday.splice(index, 1);
                                        }
                                    })
                                }
                                if (cources.week.english === _this.tempWeek[4]) {
                                    _this.friday.some(function (item, index) {
                                        if (item.id == cources.id) {
                                            _this.friday.splice(index, 1);
                                        }
                                    })
                                }
                                if (cources.week.english === _this.tempWeek[5]) {
                                    _this.saturday.some(function (item, index) {
                                        if (item.id == cources.id) {
                                            _this.saturday.splice(index, 1);
                                        }
                                    })
                                }
                                if (cources.week.english === _this.tempWeek[6]) {
                                    _this.sunday.some(function (item, index) {
                                        if (item.id == cources.id) {
                                            _this.sunday.splice(index, 1);
                                        }
                                    })
                                }
                            })
                            .catch(function (error) { // 请求失败处理
                                layer.msg("删除失败!")
                                console.log(error);
                            });
                    });
                }, 300)
            },
            editCources: function (cources) {
                const _this = this;
                let timers = this.timer;
                if (timers) {
                    window.clearTimeout(timers);
                    this.timer = null;
                }
                window.sessionStorage.setItem("star_class", strNubAry(cources.star_class)[0]);
                const index = layui.layer.open({
                    title: "编辑【" + cources.week.describe + "】课程&" + cources.storeFront.name,
                    type: 2,
                    area: ['500px', '550px'],
                    content: getRealPath() + "/admin/cources/table/edit/page",
                    success: function (layero, index) {
                        const body = layui.layer.getChildFrame('body', index);
                        const iframeWindow = window[layero.find('iframe')[0]['name']];
                        body.find("#courcesId").val(cources.id);
                        body.find(".name").val(cources.name);
                        body.find(".star_class").val(strNubAry(cources.star_class)[0]);
                        body.find(".type").val(cources.type);
                        body.find(".effect").val(cources.effect);
                        var start_end = cources.start_time + "-" + cources.end_time;
                        body.find("#start_end").each(function() {
                            // this代表的是<option></option>，对option再进行遍历
                            $(this).children("option").each(function() {
                                // 判断需要对那个选项进行回显
                                if (this.value == start_end) {
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
                        const _cources = JSON.parse(window.sessionStorage.getItem("cources"));
                        if (_cources != "" && _cources != null) {
                            if (cources.week.english === _this.tempWeek[0]) {
                                const index_ = _this.monday.indexOf(cources);
                                _this.$set(_this.monday, index_, _cources);
                            }
                            if (cources.week.english === _this.tempWeek[1]) {
                                const index_ = _this.tuesday.indexOf(cources);
                                _this.$set(_this.tuesday, index_, _cources);
                            }
                            if (cources.week.english === _this.tempWeek[2]) {
                                const index_ = _this.wednesday.indexOf(cources);
                                _this.$set(_this.wednesday, index_, _cources);
                            }
                            if (cources.week.english === _this.tempWeek[3]) {
                                const index_ = _this.thursday.indexOf(cources);
                                _this.$set(_this.thursday, index_, _cources);
                            }
                            if (cources.week.english === _this.tempWeek[4]) {
                                const index_ = _this.friday.indexOf(cources);
                                _this.$set(_this.friday, index_, _cources);
                            }
                            if (cources.week.english === _this.tempWeek[5]) {
                                const index_ = _this.saturday.indexOf(cources);
                                _this.$set(_this.saturday, index_, _cources);
                            }
                            if (cources.week.english === _this.tempWeek[6]) {
                                const index_ = _this.sunday.indexOf(cources);
                                _this.$set(_this.sunday, index_, _cources);
                            }
                            window.sessionStorage.removeItem("cources");
                        }

                    }
                });
                if(!check()){
                    layui.layer.full(index);
                    window.sessionStorage.setItem("index", index);
                    // 改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
                    $(window).on("resize", function() {
                        layui.layer.full(window.sessionStorage.getItem("index"));
                    })
                }
            },
            addCourcesPage: function (data) {
                if (this.superAdminId != 'ADMIN7826110349'){
                    layer.msg("无需添加!");
                    return;
                }
                const _this = this;
                const $data = this.$data;
                const index = layui.layer.open({
                    title: "添加【" + data.describe + "】课程&" + _this.front_name,
                    type: 2,
                    area: ['500px', '550px'],
                    content: getRealPath() + "/admin/cources/table/add/page",
                    success: function (layero, index) {
                        const body = layui.layer.getChildFrame('body', index);
                        const iframeWindow = window[layero.find('iframe')[0]['name']];
                        body.find("#weekId").val(data.id);
                        body.find("#storeFrontId").val(vm.front_id);

                    },
                    end: function () {
                        const cources = JSON.parse(window.sessionStorage.getItem("cources"));
                        if (cources != "" && cources != null) {
                            if (data.describe === "星期一") {
                                vm.monday = _this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[0], _this.tempWeek[0]);
                            }
                            if (data.describe === "星期二") {
                                vm.tuesday = _this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[1], _this.tempWeek[1]);
                            }
                            if (data.describe === "星期三") {
                                vm.wednesday = _this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[2], _this.tempWeek[2]);
                            }
                            if (data.describe === "星期四") {
                                vm.thursday = _this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[3], _this.tempWeek[3]);
                            }
                            if (data.describe === "星期五") {
                                vm.friday = _this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[4], _this.tempWeek[4]);
                            }
                            if (data.describe === "星期六") {
                                vm.saturday = _this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[5], _this.tempWeek[5]);
                            }
                            if (data.describe === "星期天") {
                                vm.sunday = _this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[6], _this.tempWeek[6]);
                            }
                            window.sessionStorage.removeItem("cources");
                        }
                    }
                });
                if(!check()){
                    layui.layer.full(index);
                    window.sessionStorage.setItem("index", index);
                    // 改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
                    $(window).on("resize", function() {
                        layui.layer.full(window.sessionStorage.getItem("index"));
                    })
                }
            }
        }
    });

})