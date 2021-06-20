
var data = [

    {name: '宁波市政府', value: 273},
    {name: '下洋涂', value: 279}
];

var geoCoordMap = {

    '宁波市政府':[121.63,29.86],
    '下洋涂':[121.740038,29.145081]
};

var convertData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if (geoCoord) {
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value)
            });
        }
    }
    return res;
};

function renderItem(params, api) {
    
    var points = [];
    for (var i = 0; i < coords.length; i++) {
        points.push(api.coord(coords[i]));
    }
    var color = api.visual('color');

    return {
        type: 'polygon',
        shape: {
            points: echarts.graphic.clipPointsByRect(points, {
                x: params.coordSys.x,
                y: params.coordSys.y,
                width: params.coordSys.width,
                height: params.coordSys.height
            })
        },
        style: api.style({
            fill: color,
            stroke: echarts.color.lift(color)
        })
    };
}

option = {
    backgroundColor: 'transparent',
    title: {
        text: '全国主要城市空气质量',
        subtext: 'data from PM25.in',
        sublink: 'http://www.pm25.in',
        left: 'center',
        textStyle: {
            color: '#fff'
        }
    },
    tooltip : {
        trigger: 'item'
    },
    bmap: {
        center: [121.71,29.24],
        zoom: 13,
        roam: true,
        mapStyle: {
            styleJson: [
                    {
                        "featureType": "water",
                        "elementType": "all",
                        "stylers": {
                            "color": "#044161"
                        }
                    },
                    {
                        "featureType": "land",
                        "elementType": "all",
                        "stylers": {
                            "color": "#004981"
                        }
                    },
                    {
                        "featureType": "boundary",
                        "elementType": "geometry",
                        "stylers": {
                            "color": "#064f85"
                        }
                    },
                    {
                        "featureType": "railway",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "highway",
                        "elementType": "geometry",
                        "stylers": {
                            "color": "#004981"
                        }
                    },
                    {
                        "featureType": "highway",
                        "elementType": "geometry.fill",
                        "stylers": {
                            "color": "#005b96",
                            "lightness": 1
                        }
                    },
                    {
                        "featureType": "highway",
                        "elementType": "labels",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "arterial",
                        "elementType": "geometry",
                        "stylers": {
                            "color": "#004981"
                        }
                    },
                    {
                        "featureType": "arterial",
                        "elementType": "geometry.fill",
                        "stylers": {
                            "color": "#00508b"
                        }
                    },
                    {
                        "featureType": "poi",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "green",
                        "elementType": "all",
                        "stylers": {
                            "color": "#056197",
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "subway",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "manmade",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "local",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "arterial",
                        "elementType": "labels",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "boundary",
                        "elementType": "geometry.fill",
                        "stylers": {
                            "color": "#029fd4"
                        }
                    },
                    {
                        "featureType": "building",
                        "elementType": "all",
                        "stylers": {
                            "color": "#1a5787"
                        }
                    },
                    {
                        "featureType": "label",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    }
            ]
        }
    },
    series : [
        {
            name: 'pm2.5',
            type: 'scatter',
            coordinateSystem: 'bmap',
            data: convertData(data),
            symbolSize: function (val) {
                return val[2] / 10;
            },
            label: {
                formatter: '{b}',
                position: 'right'
            },
            itemStyle: {
                color: '#ddb926'
            },
            emphasis: {
                label: {
                    show: true
                }
            }
        }
    ]
};