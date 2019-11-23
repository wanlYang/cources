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
    const vm = new Vue({
        el: '#app',
        data: {
            current: 0,
            timer: null,
            show:true,
            message :"温馨提示：鼠标单击删除课程,双击修改课程",
            fronts: [],
            front_id: "10132947561574263774261",
            front_name: "东郊店(金花路店)",
            week: [],
            week_id_list: ["11976352101574247112256", "21964327101574247282899", "39657101421574247168608", "47196108431574247199561", "51065329141574247214318", "67341681051574247226155", "71046358971574247275310"],
            monday: [],
            tuesday: [],
            wednesday: [],
            thursday: [],
            friday: [],
            saturday: [],
            sunday: [],
            tempWeek: ["monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"]
        },
        created: function () {
            //为了在内部函数能使用外部函数的this对象，要给它赋值了一个名叫self的变量。
            const self = this;
            //店面ID
            const front_id = this.front_id;
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
            axios.post(getRealPath() + "/admin/cources/table/week/list/front")
                .then(function (response) {
                    self.fronts = response.data.data;
                    layer.msg("获取数据成功!")
                })
                .catch(function (error) { // 请求失败处理
                    layer.msg("获取数据失败!")
                    console.log(error);
                });

            this.getWeekCources(self.front_id, self.week_id_list[0], self.tempWeek[0]).then(function (response) {
                self.monday = response.data.data;
            });
            this.getWeekCources(self.front_id, self.week_id_list[1], self.tempWeek[1]).then(function (response) {
                self.tuesday = response.data.data;
            });
            this.getWeekCources(self.front_id, self.week_id_list[2], self.tempWeek[2]).then(function (response) {
                self.wednesday = response.data.data;
            });
            this.getWeekCources(self.front_id, self.week_id_list[3], self.tempWeek[3]).then(function (response) {
                self.thursday = response.data.data;
            });
            this.getWeekCources(self.front_id, self.week_id_list[4], self.tempWeek[4]).then(function (response) {
                self.friday = response.data.data;
            });
            this.getWeekCources(self.front_id, self.week_id_list[5], self.tempWeek[5]).then(function (response) {
                self.saturday = response.data.data;
            });
            this.getWeekCources(self.front_id, self.week_id_list[6], self.tempWeek[6]).then(function (response) {
                self.sunday = response.data.data;
            });

        },
        methods: {
            getWeekCources: async function (front, week_id, describe) {
                const params_m = new URLSearchParams();
                params_m.append('front', front);
                params_m.append('week', week_id);
                return await axios.post(getRealPath() + "/admin/cources/table/list/" + describe, params_m)
            },
            storeTransformation: async function (front, index, event) {
                this.current = index;
                const el = event.currentTarget;
                vm.front_id = front.id;
                vm.front_name = front.name;
                await this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[0], this.tempWeek[0]).then(function (response) {
                    vm.monday = response.data.data;
                });
                await this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[1], this.tempWeek[1]).then(function (response) {
                    vm.tuesday = response.data.data;
                });
                await this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[2], this.tempWeek[2]).then(function (response) {
                    vm.wednesday = response.data.data;
                });
                await this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[3], this.tempWeek[3]).then(function (response) {
                    vm.thursday = response.data.data;
                });
                await this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[4], this.tempWeek[4]).then(function (response) {
                    vm.friday = response.data.data;
                });
                await this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[5], this.tempWeek[5]).then(function (response) {
                    vm.saturday = response.data.data;
                });
                await this.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[6], this.tempWeek[6]).then(function (response) {
                    vm.sunday = response.data.data;
                });

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
                        body.find(".start_time").val(cources.start_time);
                        body.find(".end_time").val(cources.end_time);
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
            },
            addCourcesPage: function (data) {
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
                                vm.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[0], _this.tempWeek[0]).then(function (response) {
                                    vm.monday = response.data.data;
                                });
                            }
                            if (data.describe === "星期二") {
                                vm.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[1], _this.tempWeek[1]).then(function (response) {
                                    vm.tuesday = response.data.data;
                                });
                            }
                            if (data.describe === "星期三") {
                                vm.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[2], _this.tempWeek[2]).then(function (response) {
                                    vm.wednesday = response.data.data;
                                });
                            }
                            if (data.describe === "星期四") {
                                vm.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[3], _this.tempWeek[3]).then(function (response) {
                                    vm.thursday = response.data.data;
                                });
                            }
                            if (data.describe === "星期五") {
                                vm.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[4], _this.tempWeek[4]).then(function (response) {
                                    vm.friday = response.data.data;
                                });
                            }
                            if (data.describe === "星期六") {
                                vm.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[5], _this.tempWeek[5]).then(function (response) {
                                    vm.saturday = response.data.data;
                                });
                            }
                            if (data.describe === "星期天") {
                                vm.$options.methods.getWeekCources(vm.front_id, vm.week_id_list[6], _this.tempWeek[6]).then(function (response) {
                                    vm.sunday = response.data.data;
                                });
                            }
                            window.sessionStorage.removeItem("cources");
                        }
                    }
                });
            }
        }
    });

})