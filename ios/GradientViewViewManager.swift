@objc(GradientViewViewManager)
class GradientViewViewManager: RCTViewManager {
    
    override func view() -> (GradientViewView) {
        return GradientViewView()
    }
    
    @objc override static func requiresMainQueueSetup() -> Bool {
        return false
    }
}

class GradientViewView : UIView {
    override class var layerClass: AnyClass { return CAGradientLayer.self }
    private var gradientLayer: CAGradientLayer { return layer as! CAGradientLayer }
    let percentFormatter: NumberFormatter = NumberFormatter()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        percentFormatter.numberStyle = .percent
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    @objc var colors: [CGColor]? {
        didSet {
            if (colors != nil){
                gradientLayer.locations = nil
                gradientLayer.colors = colors
            }
        }
    }
    
    @objc var locations: [NSNumber]? {
        didSet {
            gradientLayer.locations = locations
        }
    }
    
    @objc var start: CGPoint = CGPointMake(0.5, 0.0) {
        didSet {
            gradientLayer.startPoint = start
        }
    }
    
    @objc var end: CGPoint = CGPointMake(0.5, 1.0) {
        didSet {
            gradientLayer.endPoint = end
        }
    }
    
    @objc var colorList: [NSDictionary]? {
        didSet {
            if (colorList != nil) {
                let (cgColors, locations) = colorList!.reduce(into: ([CGColor](), [NSNumber]())) {
                    $0.0.append(RCTConvert.uiColor($1["color"]).cgColor)
                    $0.1.append(percentFormatter.number(from: $1["offset"] as! String ) ?? 0.0)
                }
                gradientLayer.colors = cgColors
                gradientLayer.locations = locations
            }
        }
    }
    
    @objc var angle: CGFloat = 0.0 {
        didSet {
            gradientLayer.apply(angle: angle)
        }
    }
}

// https://gist.github.com/JayachandraA/1248545d4d8d8556a4b1ea1bbd729180?permalink_comment_id=4200267
extension CAGradientLayer {
    func apply(angle : CGFloat) {
        let x = (180 - angle) / 360
        
        let x0 = pow(sin(.pi * (x + 0.75)), 2)
        let y0 = pow(sin(.pi * (x + 0.0)), 2)
        let x1 = pow(sin(.pi * (x + 0.25)), 2)
        let y1 = pow(sin(.pi * (x + 0.5)), 2)
        
        endPoint = CGPoint(x: x0, y: y0)
        startPoint = CGPoint(x: x1, y: y1)
    }
}
